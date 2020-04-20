package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleInfoDao;
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




    public ClickServiceImpl(ArticleInfoDao articleInfoDao, RedisTemplate<String, Serializable> redisTemplate) {
        this.articleInfoDao = articleInfoDao;
        this.redisTemplate = redisTemplate;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRecommend(ArticleInfo articleInfo) {
        articleInfo.calculatePriority();
        try{
            List<Serializable> list = redisTemplate.opsForList().range(CacheUtil.RECOMMEND,0,-1);
            List<ArticleInfo> infoList = new ArrayList<>();
            assert list!=null;
            for(Serializable se:list){
                ArticleInfo info = (ArticleInfo) se;
                info.calculatePriority();
                infoList.add(info);
            }
            if(!infoList.contains(articleInfo)) {
                infoList.add(articleInfo);
            }
            infoList.sort(ArticleInfo::compareTo);
            for (int i = 0; i < list.size(); i++) {
                redisTemplate.opsForList().leftPop(CacheUtil.RECOMMEND);
                redisTemplate.opsForList().rightPush(CacheUtil.RECOMMEND,infoList.get(i));
            }
            if(list.size()<CacheUtil.RECOMMEND_LIST_SIZE&&list.size()<infoList.size()){
                redisTemplate.opsForList().rightPush(CacheUtil.RECOMMEND,infoList.get(list.size()));
            }
        }catch (RedisCommandExecutionException e){
            redisTemplate.opsForList().leftPush(CacheUtil.RECOMMEND,articleInfo);
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
