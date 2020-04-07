package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.ArticleContent;
import edu.rorke.blog.background.management.entity.ArticleInfo;
import edu.rorke.blog.background.management.entity.dto.ArticleDto;
import edu.rorke.blog.background.management.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rorke
 * @date 2020/4/6 15:28
 */

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Boolean saveNewArticle(@RequestBody ArticleDto articleDto){
        ArticleInfo articleInfo = new ArticleInfo();
        ArticleContent articleContent = new ArticleContent();
        BeanUtils.copyProperties(articleDto,articleInfo);
        BeanUtils.copyProperties(articleDto,articleContent);
        return articleService.saveNewArticle(articleInfo,articleContent);
    }
}
