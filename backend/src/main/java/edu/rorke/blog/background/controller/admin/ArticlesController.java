package edu.rorke.blog.background.controller.admin;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.dto.ArticleDynamicSearch;
import edu.rorke.blog.background.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author Rorke
 * @date 2020/4/6 13:33
 */

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    private final ArticlesService articlesService;

    @Autowired
    public ArticlesController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @Deprecated
    @GetMapping
    public Page<ArticleInfo> getPaginationArticleInfo(@RequestParam("page")int page, @RequestParam("pageSize")int pageSize){
        return articlesService.getPaginationArticleInfo(page, pageSize);
    }

    @DeleteMapping
    public Integer[] deleteMultipleArticles(@RequestParam Integer[] articleIds){
        System.out.println(Arrays.toString(articleIds));
        return articlesService.deleteMultipleById(articleIds);
    }

    @PostMapping("/searching")
    public Page<ArticleInfo> getPaginationArticleInfoWithDynamicSearch(@RequestBody ArticleDynamicSearch articleDynamicSearch){
        String title = articleDynamicSearch.getTitle();
        int attributeId = articleDynamicSearch.getAttributeId();
        LocalDate startDate = articleDynamicSearch.getStartDate();
        LocalDate endDate = articleDynamicSearch.getEndDate();
        int page = articleDynamicSearch.getPage();
        int pageSize = articleDynamicSearch.getPageSize();
        return  articlesService.dynamicSearch(title,attributeId,startDate,endDate,page,pageSize);
    }
}

