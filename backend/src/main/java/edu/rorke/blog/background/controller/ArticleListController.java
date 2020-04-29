package edu.rorke.blog.background.controller;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.dto.ArticleDynamicSearch;
import edu.rorke.blog.background.service.ArticleListService;
import edu.rorke.blog.background.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @author Rorke
 * @date 2020/4/6 13:33
 */

@RestController
@RequestMapping("/api/articles")
public class ArticleListController {
    private final ArticleListService articleListService;

    public ArticleListController(ArticleListService articleListService) {
        this.articleListService = articleListService;
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
}

