package edu.rorke.blog.background.management.service.impl;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.repository.ArticleAndTagDao;
import edu.rorke.blog.background.management.repository.ArticleDao;
import edu.rorke.blog.background.management.service.ArticleService;
import edu.rorke.blog.background.management.service.impl.util.ArticleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Rorke
 * @date 2020/3/7 13:33
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleDao articleDao;
    private ArticleAndTagDao articleAndTagDao;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao,ArticleAndTagDao articleAndTagDao) {
        this.articleDao = articleDao;
        this.articleAndTagDao = articleAndTagDao;
    }

    @Override
    public Article findArticleById(Integer articleId) {
        return ArticleUtil.findArticleById(articleDao,articleId);
    }

    @Override
    public Integer saveArticle(Article article,List<Tag> tagList) {
        Integer articleId = ArticleUtil.getArticleIdBySaving(article,articleDao);
        return ArticleUtil.saveArticleAndTag(articleAndTagDao,articleId,tagList).size();
    }

    @Override
    public Boolean deleteArticle(Integer id){
        Article article = findArticleById(id);
        articleDao.delete(article);
        return true;
    }
}
