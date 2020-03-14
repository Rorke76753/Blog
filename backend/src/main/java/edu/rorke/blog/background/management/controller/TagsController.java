package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Rorke
 * @date 2020/3/14 11:00
 */
@RestController
@RequestMapping("/tags")
public class TagsController {
    private TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping("/page={page}&limit={limit}")
    public Page<Tag> paginationTagList(@PathVariable int page,@PathVariable int limit){
        return tagsService.paginationTagList(page,limit);
    }
}
