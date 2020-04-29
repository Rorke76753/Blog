package edu.rorke.blog.background.controller;

import edu.rorke.blog.background.entity.dto.Article;
import edu.rorke.blog.background.service.ClickService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rorke
 * @date 2020/4/18 10:08
 */
@RestController
@RequestMapping("/api/click")
public class ClickController {
    private final ClickService clickService;

    public ClickController(ClickService clickService) {
        this.clickService = clickService;
    }

    @PostMapping
    public void calculateClick(@RequestBody Article article){
        if(article.getArticleId()!=0&& !StringUtils.isEmpty(article.getIp())) {
            clickService.countClickNums(article.getArticleId(),article.getIp());
        }
    }
}
