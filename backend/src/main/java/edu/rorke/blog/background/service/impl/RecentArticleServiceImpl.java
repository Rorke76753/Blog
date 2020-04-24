package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.ArticleInfoDao;
import edu.rorke.blog.background.repository.AttributeDao;
import edu.rorke.blog.background.repository.TagDao;
import edu.rorke.blog.background.service.RecentArticleService;
import edu.rorke.blog.background.util.CacheUtil;
import edu.rorke.blog.background.util.PaginationUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/21 15:49
 */
@Service
public class RecentArticleServiceImpl implements RecentArticleService {
    private final RedisTemplate<String, Serializable> redisTemplate;
    private final TagDao tagDao;
    private final AttributeDao attributeDao;
    private final ArticleAndTagDao articleAndTagDao;
    private final ArticleInfoDao articleInfoDao;

    public RecentArticleServiceImpl(RedisTemplate<String, Serializable> redisTemplate,
                                    TagDao tagDao,
                                    AttributeDao attributeDao,
                                    ArticleAndTagDao articleAndTagDao,
                                    ArticleInfoDao articleInfoDao) {
        this.redisTemplate = redisTemplate;
        this.tagDao = tagDao;
        this.attributeDao = attributeDao;
        this.articleAndTagDao = articleAndTagDao;
        this.articleInfoDao = articleInfoDao;
    }

    @Override
    public List<ArticleInfo> getRecentArticleList() {
        List<ArticleInfo> infoList = CacheUtil.getRedisList(ArticleInfo.class,CacheUtil.ARTICLE_RECENT,redisTemplate,tagDao,articleAndTagDao,attributeDao);
        if(infoList.size()==0){
            infoList = articleInfoDao.findAll(PaginationUtil.defaultSortedPageRequest(1,5,"publishDate", Sort.Direction.DESC)).toList();
            for (ArticleInfo info: infoList) {
                CacheUtil.saveRecentArticle(info,redisTemplate,tagDao,articleAndTagDao,attributeDao);
            }
        }
        return infoList;
    }
}
