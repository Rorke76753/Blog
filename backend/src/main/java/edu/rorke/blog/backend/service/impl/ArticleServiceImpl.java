package edu.rorke.blog.backend.service.impl;

import edu.rorke.blog.backend.entity.ArticleContent;
import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.dto.Article;
import edu.rorke.blog.backend.repository.*;
import edu.rorke.blog.backend.service.ArticleService;
import edu.rorke.blog.backend.util.ArticleUtil;
import edu.rorke.blog.backend.util.CacheUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
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
        CacheUtil.saveRecentArticle(articleInfo,redisTemplate,tagDao,articleAndTagDao,attributeDao);
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
            tmpInfo.setLastUpdate(LocalDate.now());
            saveNewArticle(tmpInfo,tmpContent);
            CacheUtil.updateRecentArticle(tmpInfo,redisTemplate,tagDao,articleAndTagDao,attributeDao);
        }
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByArticleId(int articleId) {
        Optional<ArticleInfo> notNull = articleInfoDao.findById(articleId);
        notNull.ifPresent(articleInfo -> {
            ArticleUtil.modifyAttributeRelativeNum(articleInfo.getAttributeId(),-1,attributeDao);
            ArticleUtil.modifyTagRelativeNum(articleId,tagDao,articleAndTagDao);
            articleInfo.setIsDelete(1);
            articleInfoDao.save(articleInfo);
            CacheUtil.deleteElementOfKeyList(CacheUtil.ARTICLE_RECOMMEND,redisTemplate,tagDao,articleAndTagDao,attributeDao,articleInfo);
            CacheUtil.deleteElementOfKeyList(CacheUtil.ARTICLE_RECENT,redisTemplate,tagDao,articleAndTagDao,attributeDao,articleInfo);
        });
    }

    @Override
    public ArticleInfo getArticleInfoById(int articleId) {
        Optional<ArticleInfo> notNull = articleInfoDao.findByArticleIdAndIsDelete(articleId,0);
        ArticleInfo result = notNull.orElse(null);
        if(result!=null){
            ArticleUtil.appendTags(result,tagDao,articleAndTagDao);
            ArticleUtil.setAttributeName(result,attributeDao);
        }
        return result;
    }


    private Integer saveNewArticleInfo(ArticleInfo articleInfo) {
        if(articleInfo.getLastUpdate()==null){
            articleInfo.setLastUpdate(articleInfo.getPublishDate());
        }
        return articleInfoDao.save(articleInfo).getArticleId();
    }

    private Boolean saveNewArticleContent(ArticleContent articleContent) {
        articleContentDao.save(articleContent);
        return true;
    }
}
