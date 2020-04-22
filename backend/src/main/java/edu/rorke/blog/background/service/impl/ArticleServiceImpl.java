package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleContent;
import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.dto.Article;
import edu.rorke.blog.background.repository.*;
import edu.rorke.blog.background.service.ArticleService;
import edu.rorke.blog.background.util.ArticleUtil;
import edu.rorke.blog.background.util.CacheUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;


/**
 * @author Rorke
 * @date 2020/4/6 15:32
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleInfoDao articleInfoDao;
    private final ArticleAndTagDao articleAndTagDao;
    private final TagDao tagDao;
    private final AttributeDao attributeDao;
    private final ArticleContentDao articleContentDao;
    private final RedisTemplate<String, Serializable> redisTemplate;

    public ArticleServiceImpl(ArticleInfoDao articleInfoDao,
                              ArticleAndTagDao articleAndTagDao,
                              TagDao tagDao,
                              AttributeDao attributeDao,
                              ArticleContentDao articleContentDao,
                              RedisTemplate<String, Serializable> redisTemplate) {
        this.articleInfoDao = articleInfoDao;
        this.articleAndTagDao = articleAndTagDao;
        this.tagDao = tagDao;
        this.attributeDao = attributeDao;
        this.articleContentDao = articleContentDao;
        this.redisTemplate = redisTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean saveNewArticle(ArticleInfo articleInfo, ArticleContent articleContent) {
        int id = saveNewArticleInfo(articleInfo);
        articleContent.setArticleId(id);
        ArticleUtil.saveTags(id, articleInfo.getTagList(),tagDao,articleAndTagDao);
        ArticleUtil.modifyAttributeRelativeNum(articleInfo.getAttributeId(),1,attributeDao);
        CacheUtil.saveRecentArticle(articleInfo,redisTemplate);
        return saveNewArticleContent(articleContent);
    }

    @Override
    public ArticleContent getArticleContentById(int articleId) {
        return articleContentDao.findById(articleId).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateArticle(int articleId, Article article) {
        Optional<ArticleInfo> notNullInfo = articleInfoDao.findById(articleId);
        Optional<ArticleContent> notNullContent = articleContentDao.findById(articleId);
        if(notNullContent.isPresent()&&notNullInfo.isPresent()){
            ArticleInfo tmpInfo = notNullInfo.get();
            ArticleContent tmpContent = notNullContent.get();
            if(article.getAttributeId()!=tmpInfo.getAttributeId()){
                ArticleUtil.modifyAttributeRelativeNum(tmpInfo.getAttributeId(),-1,attributeDao);
            }
            BeanUtils.copyProperties(article,tmpInfo);
            BeanUtils.copyProperties(article,tmpContent);
            saveNewArticle(tmpInfo,tmpContent);
        }
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByArticleId(int articleId) {
        Optional<ArticleInfo> notNull = articleInfoDao.findById(articleId);
        notNull.ifPresent(articleInfo -> {
            ArticleUtil.modifyAttributeRelativeNum(articleInfo.getAttributeId(),-1,attributeDao);
            ArticleUtil.modifyTagRelativeNum(articleId,-1,tagDao,articleAndTagDao);
            articleInfo.setIsDelete(1);
            articleInfoDao.save(articleInfo);
            CacheUtil.deleteElementOfKeyList(CacheUtil.ARTICLE_RECOMMEND,redisTemplate,tagDao,articleAndTagDao,attributeDao,articleInfo);
            CacheUtil.deleteElementOfKeyList(CacheUtil.ARTICLE_RECENT,redisTemplate,tagDao,articleAndTagDao,attributeDao,articleInfo);
        });
    }



    private Integer saveNewArticleInfo(ArticleInfo articleInfo) {
        return articleInfoDao.save(articleInfo).getArticleId();
    }

    private Boolean saveNewArticleContent(ArticleContent articleContent) {
        articleContentDao.save(articleContent);
        return true;
    }
}
