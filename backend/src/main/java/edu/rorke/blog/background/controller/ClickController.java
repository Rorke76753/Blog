package edu.rorke.blog.background.controller;

import edu.rorke.blog.background.service.ClickService;
import edu.rorke.blog.background.util.IpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/{articleId}")
    public void calculateClick(@PathVariable int articleId, HttpServletRequest request){
        String ipAddress = IpUtil.getIpAddress(request);
        clickService.countClickNums(articleId,ipAddress);
    }
}
