package edu.rorke.blog.background.controller;


import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.Tag;
import edu.rorke.blog.background.entity.dto.TagPagination;
import edu.rorke.blog.background.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/10 14:40
 */

@RestController
@RequestMapping("/api")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tag/{tagId}/relative")
    public List<ArticleInfo> getRelativeArticleInfo(@PathVariable int tagId){
        return tagService.getRelativeArticleInfo(tagId);
    }

    @PostMapping("/tags")
    public Page<Tag> getTags(@RequestBody TagPagination tagPagination){
        int page = tagPagination.getPage();
        int pageSize = tagPagination.getPageSize();
        return tagService.getPaginationTags(page,pageSize);
    }
}
