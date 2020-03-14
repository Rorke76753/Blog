package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.service.ArticleService;
import edu.rorke.blog.background.management.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/3/6 16:06
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    private ArticleService articleService;
    private TagsService tagsService;
    @Autowired
    public ArticleController(ArticleService articleService, TagsService tagsService) {
        this.articleService = articleService;
        this.tagsService = tagsService;
    }

    /**
     * checked
     * 保存新编写的文章
     * @param article 文章，表单提交
     * @param tags    标签，表单提交
     * @return        保存是否成功
     */
    @PostMapping
    public Integer saveArticle(Article article, @RequestParam("tagsInput")String tags){
        List<Tag> tagList = tagsService.saveTags(tags);
        return articleService.saveArticle(article,tagList);
    }

    /**
     * checked
     * 获得id为articleId的文章，并填充入编写文章的表单中
     * 防止通过uri访问导致404
     * @param articleId 文章id
     * @return          文章
     */
    @GetMapping("/{articleId}")
    public Article modifyArticle(@PathVariable Integer articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * checked
     * 文章修改的保存，避免article的id是空，这里再作保险地设置了文章的id
     * 具体调用 saveArticle 方法
     * @param articleId 文章id
     * @param article   文章，表单提交
     * @param tags      标签，表单提交
     * @return          保存是否成功
     */
    @PutMapping("/{articleId}")
    public Integer updateArticle(@PathVariable Integer articleId,
                                 Article article,
                                 @RequestParam("tagsInput")String tags){
        article.setId(articleId);
        return saveArticle(article,tags);
    }

    /**
     * checked
     * 删除文章
     * @param articleId 文章id
     * @return  删除是否成功
     */
    @DeleteMapping("/{articleId}")
    public Boolean deleteArticle(@PathVariable Integer articleId){
        articleService.deleteArticle(articleId);
        return true;
    }
}
