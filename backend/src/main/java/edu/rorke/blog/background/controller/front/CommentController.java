package edu.rorke.blog.background.controller.front;

import edu.rorke.blog.background.entity.Comment;
import edu.rorke.blog.background.entity.dto.CommentDynamicSearch;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rorke
 * @date 2020/5/3 23:54
 */

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @PostMapping("/{articleId}")
    public Page<Comment> getCommentsOfArticle(@RequestBody CommentDynamicSearch commentDynamicSearch){
        return null;
    }

    @PostMapping
    public void createNewComment(@RequestBody Comment newComment){

    }
}
