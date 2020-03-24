package edu.rorke.blog.background.management.service.impl;

import edu.rorke.blog.background.management.entity.ArticleAndTag;
import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.repository.ArticleAndTagDao;
import edu.rorke.blog.background.management.repository.TagDao;
import edu.rorke.blog.background.management.service.TagListService;
import edu.rorke.blog.background.management.util.ArticleUtil;
import edu.rorke.blog.background.management.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/3/14 11:13
 */
@Service
public class TagListServiceImpl implements TagListService {
    private TagDao tagDao;
    private ArticleAndTagDao articleAndTagDao;

    @Autowired
    public TagListServiceImpl(TagDao tagDao,ArticleAndTagDao articleAndTagDao) {
        this.tagDao = tagDao;
        this.articleAndTagDao = articleAndTagDao;
    }

    @Override
    public Page<Tag> getTagPaginationList(Integer page, Integer limit) {
        return tagDao.findAll(PaginationUtil.defaultPageRequest(page,limit));
    }

    @Override
    public List<Tag> getArticleTags(Integer articleId) {
        List<ArticleAndTag> articleAndTags = articleAndTagDao.findByEmbeddedArticleId(articleId);
        List<Tag> tagList = new ArrayList<>();
        for (ArticleAndTag adt: articleAndTags) {
            Optional<Tag> tag = tagDao.findById(adt.getEmbedded().getTagId());
            tag.ifPresent(tagList::add);
        }
        return tagList;
    }

    @Override
    public List<Tag> saveTags(String tags) {
        String[] tagsArr = tags.split(";");
        List<Tag> tagList = new ArrayList<>();
        for (String tagContent : tagsArr) {
            Tag tag = new Tag();
            tag.setContent(tagContent);
            tagList.add(tag);
        }
        return ArticleUtil.saveTagList(tagList,tagDao);
    }

    @Override
    public Boolean saveOneTag(int tagId, String content) {
        Optional<Tag> tag = tagDao.findById(tagId);
        Boolean flag = Boolean.FALSE;
        if(tag.isPresent()){
            tag.get().setContent(content);
            flag = Boolean.TRUE;
        }
        return flag;
    }

    @Override
    public Boolean deleteTag(int tagId) {
        Optional<Tag> tag = tagDao.findById(tagId);
        tag.ifPresent(tag1 -> {tagDao.delete(tag1);});
        return true;
    }

    @Override
    public Boolean deleteTags(List<Integer> tags) {
        for (Integer i : tags) {
            deleteTag(i);
        }
        return true;
    }
}
