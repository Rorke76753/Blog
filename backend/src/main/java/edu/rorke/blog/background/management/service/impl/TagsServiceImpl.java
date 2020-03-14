package edu.rorke.blog.background.management.service.impl;

import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.repository.TagDao;
import edu.rorke.blog.background.management.service.TagsService;
import edu.rorke.blog.background.management.service.impl.util.ArticleUtil;
import edu.rorke.blog.background.management.service.impl.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/3/14 11:13
 */
@Service
public class TagsServiceImpl implements TagsService {
    private TagDao tagDao;

    @Autowired
    public TagsServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Page<Tag> paginationTagList(Integer page, Integer limit) {
        return tagDao.findAll(PaginationUtil.defaultPageRequest(page,limit));
    }

    @Override
    public List<Tag> getArticleTags(Integer articleId) {
        return null;
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
}
