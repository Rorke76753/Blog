package edu.rorke.blog.background.controller.admin;

import edu.rorke.blog.background.service.ArticleListService;
import edu.rorke.blog.background.service.ClickService;
import org.springframework.web.bind.annotation.*;


/**
 * @author Rorke
 * @date 2020/4/6 13:33
 */

@RestController
@RequestMapping("/api/admin/articles")
public class AdminArticleListController {
    private final ArticleListService articleListService;
    private final ClickService clickService;

    public AdminArticleListController(ArticleListService articleListService, ClickService clickService) {
        this.articleListService = articleListService;
        this.clickService = clickService;
    }

    @DeleteMapping
    public Integer[] deleteMultipleArticles(@RequestParam Integer[] articleIds){
        for (Integer i : articleIds) {
            clickService.deleteClickList(i);
        }
        return articleListService.deleteMultipleById(articleIds);
    }
}

