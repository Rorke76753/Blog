package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.Comment;
import edu.rorke.blog.background.management.service.CommentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rorke
 * @date 2020/3/15 14:33
 */
@RestController
@RequestMapping("/article/{articleId}/comments")
public class CommentListController {
    private CommentListService service;

    @Autowired
    public CommentListController(CommentListService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Comment> paginationCommentList(@PathVariable Integer articleId){
        return paginationCommentList(articleId,0,10);
    }

    @GetMapping("/page={page}&limit={limit}")
    public Page<Comment> paginationCommentList(@PathVariable Integer articleId,
                                               @PathVariable Integer page,
                                               @PathVariable Integer limit){
        return service.getCommentPaginationList(articleId,page,limit);
    }

    @PutMapping("/{commentId}")
    public Boolean showComment(@PathVariable Integer commentId){
        return service.showComment(commentId);
    }

    /**
     * 真删除
     * @param commentId 评论id
     * @return          删除结果
     */
    @DeleteMapping("/{commentId}")
    public Boolean deleteComment(@PathVariable Integer commentId){
        return service.deleteComment(commentId);
    }
}
