package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleAndTag;
import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.AttributeDao;
import edu.rorke.blog.background.repository.TagDao;
import edu.rorke.blog.background.service.RecentArticleService;
import edu.rorke.blog.background.util.CacheUtil;
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

    public RecentArticleServiceImpl(RedisTemplate<String, Serializable> redisTemplate,
                                    TagDao tagDao,
                                    AttributeDao attributeDao,
                                    ArticleAndTagDao articleAndTagDao) {
        this.redisTemplate = redisTemplate;
        this.tagDao = tagDao;
        this.attributeDao = attributeDao;
        this.articleAndTagDao = articleAndTagDao;
    }

    @Override
    public List<ArticleInfo> getRecentArticleList() {
        return CacheUtil.getRedisList(ArticleInfo.class,CacheUtil.ARTICLE_RECENT,redisTemplate,tagDao,articleAndTagDao,attributeDao);
    }
}
