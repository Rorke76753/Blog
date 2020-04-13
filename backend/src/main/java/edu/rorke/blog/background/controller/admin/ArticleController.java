package edu.rorke.blog.background.controller.admin;

import edu.rorke.blog.background.entity.ArticleContent;
import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.dto.Article;
import edu.rorke.blog.background.service.ArticleService;
import org.springframework.beans.BeanUtils;
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

    @PutMapping("/{articleId}")
    public Boolean updateArticle(@PathVariable int articleId,@RequestBody Article article){
        return articleService.updateArticle(articleId, article);
    }

    @PostMapping
    public Boolean saveNewArticle(@RequestBody Article article){
        ArticleInfo articleInfo = new ArticleInfo();
        ArticleContent articleContent = new ArticleContent();
        BeanUtils.copyProperties(article,articleInfo);
        BeanUtils.copyProperties(article,articleContent);
        return articleService.saveNewArticle(articleInfo,articleContent);
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable int articleId){
        articleService.deleteByArticleId(articleId);
    }
}
