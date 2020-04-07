package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.ArticleInfo;
import edu.rorke.blog.background.management.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rorke
 * @date 2020/4/6 13:33
 */

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    private ArticlesService articlesService;

    @Autowired
    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping
    public Page<ArticleInfo> getPaginationArticles(@RequestParam("page")int page, @RequestParam("pageSize")int pageSize){
        return articlesService.getPaginationArticles(page, pageSize);
    }
}
