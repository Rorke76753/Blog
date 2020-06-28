package edu.rorke.blog.backend.controller.admin;


import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.Tag;
import edu.rorke.blog.backend.entity.dto.TagPagination;
import edu.rorke.blog.backend.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Rorke
 * @date 2020/4/10 14:40
 */

@RestController
@RequestMapping("/api/admin/tag")
public class AdminTagController {
    private final TagService tagService;

    public AdminTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{tagId}/relative")
    public List<ArticleInfo> getRelativeArticleInfo(@PathVariable int tagId){
        return tagService.getRelativeArticleInfo(tagId);
    }
    @PostMapping("/tags")
    public Page<Tag> getTagPagination(@RequestBody TagPagination tagPagination){
        return tagService.getPaginationTags(tagPagination.getPage(),tagPagination.getPageSize(),tagPagination.getSortBy());
    }

    @PutMapping("/{tagId}")
    public Tag updateTagContent(@PathVariable int tagId,
                                @RequestBody Tag newTag){
        if(tagId == newTag.getTagId()) {
            return tagService.updateTag(tagId, newTag);
        }
        return new Tag();
    }

}
