package edu.rorke.blog.backend.controller.front;


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
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAllTags(){
        return tagService.getAllTag();
    }

    @PostMapping("/wall")
    public Page<Tag> getTagWall(@RequestBody TagPagination tagPagination){
        int page = tagPagination.getPage();
        int pageSize = tagPagination.getPageSize();
        String sortBy = tagPagination.getSortBy();
        return tagService.getPaginationTags(page,pageSize,sortBy);
    }
}
