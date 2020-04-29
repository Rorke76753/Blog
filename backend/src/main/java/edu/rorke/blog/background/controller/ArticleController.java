package edu.rorke.blog.background.controller;

import edu.rorke.blog.background.entity.ArticleContent;
import edu.rorke.blog.background.service.ArticleService;
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

    @GetMapping("/{articleId}")
    public ArticleContent getArticle(@PathVariable int articleId){
        return articleService.getArticleContentById(articleId);
    }
}
