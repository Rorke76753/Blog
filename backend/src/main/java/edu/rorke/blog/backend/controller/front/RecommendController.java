package edu.rorke.blog.backend.controller.front;


import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/20 22:58
 */
@RestController
@RequestMapping("/api/recommend")
public class RecommendController {
    private final RecommendService recommendService;

    @Autowired
    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping
    public List<ArticleInfo> getRecommendList(){
        return recommendService.getRecommendList();
    }
}
