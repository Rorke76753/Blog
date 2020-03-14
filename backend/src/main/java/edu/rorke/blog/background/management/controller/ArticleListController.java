package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.dto.ArticleDto;
import edu.rorke.blog.background.management.service.ArticleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/3/11 12:02
 */
@RestController
@RequestMapping("/articles")
public class ArticleListController {
    private ArticleListService articleListService;

    @Autowired
    public ArticleListController(ArticleListService articleListService) {
        this.articleListService = articleListService;
    }

    @GetMapping
    public List<Article> allArticleList(){
        return articleListService.getArticleList();
    }

    /**
     * 分页获得文章列表
     * @param page  页数
     * @param limit 大小
     * @return      列表
     */
    @GetMapping("/page={page}&limit={limit}")
    public Page<ArticleDto> paginationArticleList(@PathVariable int page, @PathVariable int limit){
       return articleListService.getBriefArticleListWithPagination(page,limit);
    }

    @GetMapping("/searchTitle={title}&page={page}&limit={limit}")
    public Page<ArticleDto> searchArticleWithTitle(@PathVariable String title,
                                                   @PathVariable int page,
                                                   @PathVariable int limit){
        title = "%"+title+"%";
        return articleListService.findArticleByTitle(title,page,limit);
    }

    @DeleteMapping
    public Boolean deleteArticles(@RequestParam List<Integer> deleteList){
        return articleListService.deleteArticles(deleteList);
    }
}
