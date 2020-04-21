package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.ArticleInfoDao;
import edu.rorke.blog.background.repository.AttributeDao;
import edu.rorke.blog.background.repository.TagDao;
import edu.rorke.blog.background.service.ClickService;
import edu.rorke.blog.background.util.CacheUtil;
import io.lettuce.core.RedisCommandExecutionException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String cacheKey = CacheUtil.CLICK_PREFIX + articleId + CacheUtil.CLICK_SUFFIX;
        try{
            List<Serializable> list = redisTemplate.opsForList().range(cacheKey,0,-1);
            assert list != null;
            if(!list.contains(ipAddress)){
                if (list.size() >= CacheUtil.RECENT_VIEW_LIST_SIZE) {
                    redisTemplate.opsForList().leftPop(cacheKey);
                }
                redisTemplate.opsForList().rightPush(cacheKey,ipAddress);
                increaseClickNum(articleId);
            }
        }catch (RedisCommandExecutionException e){
            redisTemplate.opsForList().leftPush(cacheKey,ipAddress);
            increaseClickNum(articleId);
        }
    }

    /**
     * 更新推荐列表
     * @param articleInfo 文章信息
     */
    @Transactional(rollbackFor = Exception.class)
    protected void updateRecommend(ArticleInfo articleInfo) {
        articleInfo.calculatePriority();
        List<ArticleInfo> infoList = CacheUtil.getRedisList(ArticleInfo.class,CacheUtil.ARTICLE_RECOMMEND,redisTemplate,tagDao,articleAndTagDao,attributeDao);
        if(infoList.size()!=0) {
            if(!infoList.contains(articleInfo)) {
                infoList.add(articleInfo);
            }
            infoList.sort(ArticleInfo::compareTo);
            int popCnt = Math.toIntExact(redisTemplate.opsForList().size(CacheUtil.ARTICLE_RECOMMEND));
            for (int i = 0; i < popCnt; i++) {
                redisTemplate.opsForList().leftPop(CacheUtil.ARTICLE_RECOMMEND);
                redisTemplate.opsForList().rightPush(CacheUtil.ARTICLE_RECOMMEND,infoList.get(i));
            }
            if(popCnt<CacheUtil.RECOMMEND_LIST_SIZE&&popCnt<infoList.size()){
                redisTemplate.opsForList().rightPush(CacheUtil.ARTICLE_RECOMMEND,infoList.get(popCnt));
            }
        }else {
            redisTemplate.opsForList().leftPush(CacheUtil.ARTICLE_RECOMMEND, articleInfo);
        }
    }

    private void increaseClickNum(int articleId){
        Optional<ArticleInfo> notNull = articleInfoDao.findById(articleId);
        //TODO: if optional is null
        notNull.ifPresent(articleInfo -> {
            int clickNum = articleInfo.getClickNum() + 1;
            articleInfo.setClickNum(clickNum);
            articleInfoDao.save(articleInfo);
            updateRecommend(articleInfo);
        });
    }
}
