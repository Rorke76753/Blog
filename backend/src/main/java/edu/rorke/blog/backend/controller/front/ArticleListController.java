package edu.rorke.blog.backend.controller.front;

import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.dto.ArticleDynamicSearch;
import edu.rorke.blog.backend.service.ArticleListService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
        Integer page = articleDynamicSearch.getPage();
        Integer pageSize = articleDynamicSearch.getPageSize();
        String orderBy = articleDynamicSearch.getOrderBy();
        return  articleListService.dynamicSearch(null,null,null,null,page,pageSize,orderBy);
    }
}

