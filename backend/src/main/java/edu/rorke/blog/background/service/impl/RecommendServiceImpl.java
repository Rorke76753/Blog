package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.TagDao;
import edu.rorke.blog.background.service.RecommendService;
import edu.rorke.blog.background.util.ArticleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/20 23:01
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    private final RedisTemplate<String, Serializable> redisTemplate;
    private final String RECOMMEND = "ARTICLE_RECOMMEND";
    private final TagDao tagDao;
    private final ArticleAndTagDao articleAndTagDao;

    @Autowired
    public RecommendServiceImpl(RedisTemplate<String, Serializable> redisTemplate, TagDao tagDao, ArticleAndTagDao articleAndTagDao) {
        this.redisTemplate = redisTemplate;
        this.tagDao = tagDao;
        this.articleAndTagDao = articleAndTagDao;
    }

    @Override
    public List<ArticleInfo> getRecommendList() {
        List<Serializable> list = redisTemplate.opsForList().range(RECOMMEND,0,-1);
        List<ArticleInfo> infoList = new ArrayList<>();
        for (Serializable se : list) {
            ArticleInfo info = (ArticleInfo) se;
            ArticleUtil.appendTags(info,tagDao,articleAndTagDao);
            infoList.add(info);
        }
        return infoList;
    }
}
