package edu.rorke.blog.background.management;

import edu.rorke.blog.background.management.repository.ArticleAndTagDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogBackgroundManagementApplicationTests {
    @Autowired
    private ArticleAndTagDao articleAndTagDao;

    @Test
    void embeddedKeyQueryTest() {
        articleAndTagDao.findByEmbeddedArticleId(6).forEach(articleAndTag -> System.out.println(articleAndTag.toString()));
        articleAndTagDao.findByEmbeddedTagId(3).forEach(articleAndTag -> System.out.println(articleAndTag.toString()));
    }

}
