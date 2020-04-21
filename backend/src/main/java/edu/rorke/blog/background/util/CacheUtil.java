package edu.rorke.blog.background.util;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.AttributeDao;
import edu.rorke.blog.background.repository.TagDao;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Rorke
 * @date 2020/4/20 23:28
 */

public class CacheUtil {
    public static final Integer RECENT_VIEW_LIST_SIZE = 100;
    public static final Integer RECOMMEND_LIST_SIZE = 5;
    public static final Integer RECENT_LIST_SIZE = 5;
    public static final String CLICK_PREFIX = "ARTICLE_";
    public static final String CLICK_SUFFIX = "_CLICK_IP";
    public static final String ARTICLE_RECOMMEND = "ARTICLE_RECOMMEND";
    public static final String ARTICLE_RECENT = "ARTICLE_RECENT";

    /**
     * 获得redis中list类型的数据
     *
     * @param type          反序列化后的类
     * @param key           redis中的key值
     * @param redisTemplate 操作redis
     * @param <T>           泛型，与反序列化后的类相同
     * @return T类型的列表
     */
    public static <T> List<T> getRedisList(Class<T> type,
                                           @NotNull String key,
                                           RedisTemplate<String, Serializable> redisTemplate,
                                           TagDao tagDao,
                                           ArticleAndTagDao articleAndTagDao,
                                           AttributeDao attributeDao){
        List<T> resultList = new ArrayList<>();
        if (!Objects.equals(redisTemplate.hasKey(key), Boolean.FALSE)) {
            List<Serializable> list = redisTemplate.opsForList().range(key, 0, -1);
            assert list != null;
            for (Serializable se : list) {
                T target = (T) se;
                if (type == ArticleInfo.class) {
                    articleInfoOperation((ArticleInfo) target, tagDao, articleAndTagDao,attributeDao);
                }
                resultList.add(target);
            }
        }
        return resultList;
    }

    private static void articleInfoOperation(ArticleInfo info, TagDao tagDao, ArticleAndTagDao articleAndTagDao, AttributeDao attributeDao) {
        info.calculatePriority();
        ArticleUtil.setAttributeName(info,attributeDao);
        ArticleUtil.appendTags(info, tagDao, articleAndTagDao);
    }

    /**
     * 删除推荐，“删除”一篇文章的时候这篇文章可能在推荐列表上，这时候就需要删除在推荐列表上的这篇文章
     * @param articleInfo 删除的文章信息
     * @param redisTemplate 操作缓存
     * @param tagDao 标签dao
     * @param articleAndTagDao 文章与标签关系dao
     * @param attributeDao 属性dao
     */
    public static void deleteRecommend(ArticleInfo articleInfo,RedisTemplate<String,Serializable> redisTemplate,TagDao tagDao,ArticleAndTagDao articleAndTagDao,AttributeDao attributeDao) {
        articleInfoOperation(articleInfo,tagDao,articleAndTagDao,attributeDao);
        redisTemplate.opsForList().remove(ARTICLE_RECOMMEND,0,articleInfo);
    }

    public static void updateRecentArticle(ArticleInfo articleInfo, RedisTemplate<String, Serializable> redisTemplate) {
        if(Objects.equals(redisTemplate.hasKey(ARTICLE_RECENT),Boolean.TRUE)){
            int cacheRecentSize = Math.toIntExact(redisTemplate.opsForList().size(ARTICLE_RECENT));
            if(cacheRecentSize == RECENT_LIST_SIZE){
                redisTemplate.opsForList().leftPop(ARTICLE_RECENT);
            }
        }
        redisTemplate.opsForList().rightPush(ARTICLE_RECENT,articleInfo);
    }
}
