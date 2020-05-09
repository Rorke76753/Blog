package edu.rorke.blog.backend.controller.front;

import edu.rorke.blog.backend.entity.ArticleContent;
import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rorke
 * @date 2020/4/6 15:28
 */

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/info/{articleId}")
    public ArticleInfo getArticleInfo(@PathVariable int articleId){
        return articleService.getArticleInfoById(articleId);
    }

    @GetMapping("/{articleId}")
    public ArticleContent getArticleContent(@PathVariable int articleId){
        return articleService.getArticleContentById(articleId);
    }
}
