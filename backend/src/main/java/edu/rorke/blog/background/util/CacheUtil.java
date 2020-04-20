package edu.rorke.blog.background.util;

import io.lettuce.core.RedisCommandExecutionException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
    public static final String CLICK_PREFIX = "ARTICLE_";
    public static final String CLICK_SUFFIX = "_CLICK_IP";
    public static final String RECOMMEND = "ARTICLE_RECOMMEND";

    /**
     * 获得redis中list类型的数据
     * @param type 反序列化后的类
     * @param key  redis中的key值
     * @param redisTemplate 操作redis
     * @param <T> 泛型，与反序列化后的类相同
     * @return T类型的列表
     */
    public static <T> List<T> getRedisList(Class<T> type, @NotNull String key, RedisTemplate<String, Serializable> redisTemplate){
        List<T> resultList = new ArrayList<>();
        try{
            if(!Objects.equals(redisTemplate.hasKey(key), Boolean.FALSE)) {
                List<Serializable> list = redisTemplate.opsForList().range(key, 0, -1);
                assert list != null;
                for (Serializable se : list) {
                    T target = (T) se;
                    resultList.add(target);
                }
                return resultList;
            }
        }catch (RedisCommandExecutionException e){
            //TODO: logging
        }
        return resultList;
    }
}
