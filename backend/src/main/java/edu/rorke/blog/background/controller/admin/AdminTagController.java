package edu.rorke.blog.background.controller.admin;


import edu.rorke.blog.background.entity.Tag;
import edu.rorke.blog.background.service.TagService;
import org.springframework.web.bind.annotation.*;


/**
 * @author Rorke
 * @date 2020/4/10 14:40
 */

@RestController
@RequestMapping("/api/admin")
public class AdminTagController {
    private final TagService tagService;

    public AdminTagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PutMapping("/tag/{tagId}")
    public Tag updateTagContent(@PathVariable int tagId,
                                @RequestBody Tag newTag){
        if(tagId == newTag.getTagId()) {
            return tagService.updateTag(tagId, newTag);
        }
        return new Tag();
    }
}
