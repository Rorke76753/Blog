package edu.rorke.blog.backend.controller.admin;

import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.dto.ArticleDynamicSearch;
import edu.rorke.blog.backend.service.ArticleListService;
import edu.rorke.blog.backend.service.ClickService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


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

    @PostMapping
    public Page<ArticleInfo> getPaginationArticleInfoWithDynamicSearch(@RequestBody ArticleDynamicSearch articleDynamicSearch){
        String title = articleDynamicSearch.getTitle();
        Integer attributeId = articleDynamicSearch.getAttributeId();
        LocalDate startDate = articleDynamicSearch.getStartDate();
        LocalDate endDate = articleDynamicSearch.getEndDate();
        Integer page = articleDynamicSearch.getPage();
        Integer pageSize = articleDynamicSearch.getPageSize();
        String orderBy = articleDynamicSearch.getOrderBy();
        return  articleListService.dynamicSearch(title,attributeId,startDate,endDate,page,pageSize,orderBy);
    }

    @DeleteMapping
    public Integer[] deleteMultipleArticles(@RequestParam Integer[] articleIds){
        for (Integer i : articleIds) {
            clickService.deleteClickList(i);
        }
        return articleListService.deleteMultipleById(articleIds);
    }
}

