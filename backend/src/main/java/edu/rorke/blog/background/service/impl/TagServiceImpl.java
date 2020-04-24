package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleAndTag;
import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.Tag;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.ArticleInfoDao;
import edu.rorke.blog.background.repository.AttributeDao;
import edu.rorke.blog.background.repository.TagDao;
import edu.rorke.blog.background.service.TagService;
import edu.rorke.blog.background.util.ArticleUtil;
import edu.rorke.blog.background.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Page<Tag> getPaginationTags(int page, int pageSize) {
        return tagDao.findAll(PaginationUtil.defaultSortedPageRequest(page, pageSize, "tagId", Sort.Direction.ASC));
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
}
