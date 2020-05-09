package edu.rorke.blog.backend.service.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import javax.persistence.criteria.Predicate;
import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.repository.ArticleAndTagDao;
import edu.rorke.blog.backend.repository.ArticleInfoDao;
import edu.rorke.blog.backend.repository.AttributeDao;
import edu.rorke.blog.backend.repository.TagDao;
import edu.rorke.blog.backend.service.ArticleListService;
import edu.rorke.blog.backend.util.ArticleUtil;
import edu.rorke.blog.backend.util.CacheUtil;
import edu.rorke.blog.backend.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rorke
 * @date 2020/4/6 13:54
 */
@Service
public class ArticleListServiceImpl implements ArticleListService {
    private final ArticleInfoDao articleInfoDao;
    private final ArticleAndTagDao articleAndTagDao;
    private final TagDao tagDao;
    private final AttributeDao attributeDao;
    private final RedisTemplate<String, Serializable> redisTemplate;

    public ArticleListServiceImpl(ArticleInfoDao articleInfoDao,
                                  ArticleAndTagDao articleAndTagDao,
                                  TagDao tagDao,
                                  AttributeDao attributeDao,
                                  RedisTemplate<String, Serializable> redisTemplate) {
        this.articleInfoDao = articleInfoDao;
        this.articleAndTagDao = articleAndTagDao;
        this.tagDao = tagDao;
        this.attributeDao = attributeDao;
        this.redisTemplate = redisTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer[] deleteMultipleById(Integer[] articleIds) {
        int isDelete = 1;
        List<Integer> success = new ArrayList<>();
        List<ArticleInfo> infos = articleInfoDao.findAllById(Arrays.asList(articleIds));
        for (ArticleInfo info : infos) {
            info.setIsDelete(1);
            int attributeId = info.getAttributeId();
            ArticleUtil.modifyAttributeRelativeNum(attributeId, -1, attributeDao);
            ArticleUtil.modifyTagRelativeNum(info.getArticleId(), tagDao, articleAndTagDao);
            info.setIsDelete(isDelete);
            articleInfoDao.save(info);
            success.add(info.getArticleId());
        }
        ArticleInfo[] articleInfos = infos.toArray(new ArticleInfo[0]);
        CacheUtil.deleteElementOfKeyList(CacheUtil.ARTICLE_RECOMMEND, redisTemplate, tagDao, articleAndTagDao, attributeDao, articleInfos);
        CacheUtil.deleteElementOfKeyList(CacheUtil.ARTICLE_RECENT, redisTemplate, tagDao, articleAndTagDao, attributeDao, articleInfos);
        return success.toArray(new Integer[0]);
    }

    @Override
    public Page<ArticleInfo> dynamicSearch(String title, Integer attributeId, LocalDate startDate, LocalDate endDate, int page, int pageSize, String orderBy) {
        Specification<ArticleInfo> specification = (Specification<ArticleInfo>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //it is not deleted
            predicateList.add(criteriaBuilder.equal(root.get("isDelete"), 0));

            if (title != null && !title.isEmpty()) {
                predicateList.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            if (attributeId != null && attributeId != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("attributeId"), attributeId));
            }
            if (startDate != null && endDate != null) {
                predicateList.add(criteriaBuilder.between(root.get("publishDate"), startDate, endDate));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
        Sort.Direction direction = Sort.Direction.DESC;
        if (orderBy == null || orderBy.isEmpty()) {
            orderBy = "articleId";
            direction = Sort.Direction.ASC;
        }
        Page<ArticleInfo> articleInfos =
                articleInfoDao
                        .findAll(specification,
                                PaginationUtil.defaultSortedPageRequest(page, pageSize, orderBy, direction));
        for (ArticleInfo articleInfo : articleInfos) {
            ArticleUtil.setAttributeName(articleInfo, attributeDao);
            ArticleUtil.appendTags(articleInfo, tagDao, articleAndTagDao);
        }
        return articleInfos;
    }
}
