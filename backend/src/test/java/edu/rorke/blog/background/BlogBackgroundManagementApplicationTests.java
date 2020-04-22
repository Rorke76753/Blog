package edu.rorke.blog.background;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.repository.ArticleAndTagDao;
import edu.rorke.blog.background.repository.ArticleInfoDao;
import edu.rorke.blog.background.repository.AttributeDao;
import edu.rorke.blog.background.repository.TagDao;
import edu.rorke.blog.background.util.CacheUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Optional;


@SpringBootTest
class BlogBackgroundManagementApplicationTests {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private ArticleInfoDao articleInfoDao;
    @Autowired
    private ArticleAndTagDao articleAndTagDao;
    @Autowired
    private AttributeDao attributeDao;
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    void cacheDeleteTest() {
        Optional<ArticleInfo> info = articleInfoDao.findById(8);
        info.ifPresent(articleInfo -> {
        });
    }

}
