package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.service.TagListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


/**
 * @author Rorke
 * @date 2020/3/14 11:00
 */
@RestController
@RequestMapping("/tags")
public class TagListController {
    private TagListService tagListService;

    @Autowired
    public TagListController(TagListService tagListService) {
        this.tagListService = tagListService;
    }

    @GetMapping("/page={page}&limit={limit}")
    public Page<Tag> paginationTagList(@PathVariable int page,@PathVariable int limit){
        return tagListService.getTagPaginationList(page,limit);
    }

    @PutMapping("/{tagId}")
    public Boolean saveTag(@PathVariable int tagId,String content){
        return tagListService.saveOneTag(tagId,content);
    }

    @DeleteMapping("/{tagId}")
    public Boolean deleteTag(@PathVariable int tagId){
        return tagListService.deleteTag(tagId);
    }
}
