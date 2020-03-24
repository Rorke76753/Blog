package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.service.TagListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Rorke
 * @date 2020/3/14 11:00
 */
@RestController
@RequestMapping("/tags")
public class TagListController {
    private TagListService service;

    @Autowired
    public TagListController(TagListService service) {
        this.service = service;
    }

    @GetMapping("/page={page}&limit={limit}")
    public Page<Tag> paginationTagList(@PathVariable int page,@PathVariable int limit){
        return service.getTagPaginationList(page,limit);
    }

    @PutMapping("/{tagId}")
    public Boolean saveTag(@PathVariable int tagId,String content){
        return service.saveOneTag(tagId,content);
    }

    @DeleteMapping("/{tagId}")
    public Boolean deleteTag(@PathVariable int tagId){
        return service.deleteTag(tagId);
    }

    @DeleteMapping
    public Boolean deleteTags(@RequestParam List<Integer> tags){
        return service.deleteTags(tags);
    }
}
