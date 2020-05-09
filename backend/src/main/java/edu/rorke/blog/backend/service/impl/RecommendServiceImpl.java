package edu.rorke.blog.backend.service.impl;

import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.repository.ArticleAndTagDao;
import edu.rorke.blog.backend.repository.AttributeDao;
import edu.rorke.blog.backend.repository.TagDao;
import edu.rorke.blog.backend.service.RecommendService;
import edu.rorke.blog.backend.util.CacheUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/20 23:01
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    private final RedisTemplate<String, Serializable> redisTemplate;
    private final TagDao tagDao;
    private final ArticleAndTagDao articleAndTagDao;
    private final AttributeDao attributeDao;

    public RecommendServiceImpl(RedisTemplate<String, Serializable> redisTemplate,
                                TagDao tagDao,
                                ArticleAndTagDao articleAndTagDao,
                                AttributeDao attributeDao) {
        this.redisTemplate = redisTemplate;
        this.tagDao = tagDao;
        this.articleAndTagDao = articleAndTagDao;
        this.attributeDao = attributeDao;
    }

    @Override
    public List<ArticleInfo> getRecommendList() {
        return CacheUtil.getRedisList(ArticleInfo.class,CacheUtil.ARTICLE_RECOMMEND,redisTemplate,tagDao,articleAndTagDao,attributeDao);
    }
}
