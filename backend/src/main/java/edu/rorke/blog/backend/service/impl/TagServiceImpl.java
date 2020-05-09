package edu.rorke.blog.backend.service.impl;

import edu.rorke.blog.backend.entity.ArticleAndTag;
import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.Tag;
import edu.rorke.blog.backend.repository.ArticleAndTagDao;
import edu.rorke.blog.backend.repository.ArticleInfoDao;
import edu.rorke.blog.backend.repository.AttributeDao;
import edu.rorke.blog.backend.repository.TagDao;
import edu.rorke.blog.backend.service.TagService;
import edu.rorke.blog.backend.util.ArticleUtil;
import edu.rorke.blog.backend.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/11 14:31
 */
@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final ArticleAndTagDao articleAndTagDao;
    private final ArticleInfoDao articleInfoDao;
    private final AttributeDao attributeDao;

    public TagServiceImpl(TagDao tagDao, ArticleAndTagDao articleAndTagDao, ArticleInfoDao articleInfoDao, AttributeDao attributeDao) {
        this.tagDao = tagDao;
        this.articleAndTagDao = articleAndTagDao;
        this.articleInfoDao = articleInfoDao;
        this.attributeDao = attributeDao;
    }

    @Override
    public Page<Tag> getPaginationTags(int page, int pageSize, String sortBy) {
        return tagDao.findAll(PaginationUtil.defaultSortedPageRequest(page, pageSize, sortBy, Sort.Direction.DESC));
    }

    @Override
    public Tag updateTag(int tagId, Tag newTag) {
        return tagDao.saveAndFlush(newTag);
    }

    @Override
    public List<ArticleInfo> getRelativeArticleInfo(int tagId) {
        List<ArticleAndTag> relations = articleAndTagDao.findAllByTagId(tagId);
        List<Integer> articleId = new ArrayList<>();
        for (ArticleAndTag relation : relations) {
            articleId.add(relation.getArticleId());
        }
        List<ArticleInfo> infos = articleInfoDao.findAllById(articleId);
        for(ArticleInfo info:infos){
            ArticleUtil.setAttributeName(info,attributeDao);
        }
        return infos;
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.findAll();
    }
}
