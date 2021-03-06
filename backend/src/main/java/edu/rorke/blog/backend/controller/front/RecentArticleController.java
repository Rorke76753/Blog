package edu.rorke.blog.backend.controller.front;

import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.service.RecentArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/21 15:42
 */
@RestController
@RequestMapping("/api/recent")
public class RecentArticleController {
    private final RecentArticleService recentArticleService;

    public RecentArticleController(RecentArticleService recentArticleService) {
        this.recentArticleService = recentArticleService;
    }

    @GetMapping
    public List<ArticleInfo> getRecentArticleList(){
        return recentArticleService.getRecentArticleList();
    }
}
