package edu.rorke.blog.backend;

import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.repository.ArticleAndTagDao;
import edu.rorke.blog.backend.repository.ArticleInfoDao;
import edu.rorke.blog.backend.repository.AttributeDao;
import edu.rorke.blog.backend.repository.TagDao;
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
