package edu.rorke.blog.backend.controller.admin;

import edu.rorke.blog.backend.entity.ArticleContent;
import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.dto.Article;
import edu.rorke.blog.backend.service.ArticleService;
import edu.rorke.blog.backend.service.ClickService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author Rorke
 * @date 2020/4/29 16:02
 */
@RestController
@RequestMapping("/api/admin/article")
public class AdminArticleController {
    private final ArticleService articleService;
    private final ClickService clickService;

    @Autowired
    public AdminArticleController(ArticleService articleService, ClickService clickService) {
        this.articleService = articleService;
        this.clickService = clickService;
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
        articleInfo.setLastUpdate(LocalDate.now());
        BeanUtils.copyProperties(article,articleContent);
        return articleService.saveNewArticle(articleInfo,articleContent);
    }

    @DeleteMapping("/{articleId}")
    public void deleteArticle(@PathVariable int articleId){
        articleService.deleteByArticleId(articleId);
        clickService.deleteClickList(articleId);
    }
}
