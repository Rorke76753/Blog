package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.ArticleInfoDao;
import edu.rorke.blog.background.repository.AttributeDao;
import edu.rorke.blog.background.repository.TagDao;
import edu.rorke.blog.background.service.ClickService;
import edu.rorke.blog.background.util.CacheUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * @author Rorke
 * @date 2020/4/18 13:26
 */
@Service
public class ClickServiceImpl implements ClickService {
    private final ArticleInfoDao articleInfoDao;
    private final RedisTemplate<String, Serializable> redisTemplate;
    private final TagDao tagDao;
    private final ArticleAndTagDao articleAndTagDao;
    private final AttributeDao attributeDao;

    public ClickServiceImpl(ArticleInfoDao articleInfoDao,
                            RedisTemplate<String, Serializable> redisTemplate,
                            TagDao tagDao,
                            ArticleAndTagDao articleAndTagDao,
                            AttributeDao attributeDao) {
        this.articleInfoDao = articleInfoDao;
        this.redisTemplate = redisTemplate;
        this.tagDao = tagDao;
        this.articleAndTagDao = articleAndTagDao;
        this.attributeDao = attributeDao;
    }

    @Override
    public void countClickNums(int articleId, String ipAddress) {
        String clickListKey = CacheUtil.CLICK_PREFIX + articleId + CacheUtil.CLICK_SUFFIX;
        List<String> list = CacheUtil.getRedisList(String.class,clickListKey,redisTemplate,tagDao,articleAndTagDao,attributeDao);
        boolean containsFlag = list.contains(ipAddress);
        if(!containsFlag){
            redisTemplate.opsForList().rightPush(clickListKey, ipAddress);
            increaseClickNum(articleId);
            if (list.size() >= CacheUtil.RECENT_VIEW_LIST_SIZE) {
                redisTemplate.opsForList().leftPop(clickListKey);
            }
        }

    }

    @Override
    public void deleteClickList(int articleId) {
        String clickListKey = CacheUtil.CLICK_PREFIX + articleId + CacheUtil.CLICK_SUFFIX;
        if(Objects.equals(redisTemplate.hasKey(clickListKey),Boolean.TRUE)){
            redisTemplate.delete(clickListKey);
        }
    }

    private void increaseClickNum(int articleId){
        Optional<ArticleInfo> notNull = articleInfoDao.findById(articleId);
        //TODO: if optional is null
        notNull.ifPresent(articleInfo -> {
            int clickNum = articleInfo.getClickNum() + 1;
            articleInfo.setClickNum(clickNum);
            articleInfoDao.save(articleInfo);
            CacheUtil.updateRecommend(articleInfo,redisTemplate,tagDao,articleAndTagDao,attributeDao);
        });
    }
}
