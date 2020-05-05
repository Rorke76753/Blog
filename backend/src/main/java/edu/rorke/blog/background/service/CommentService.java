package edu.rorke.blog.background.service;

import edu.rorke.blog.background.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/5/4 0:10
 */
public interface CommentService {
    /**
     * 获得某一文章下的评论列表
     * @param articleId 文章id
     * @return 评论列表
     */
    List<Comment> getCommentsOfArticle(int articleId);

    /**
     * 增加新的评论
     * @param newComment
     * @return
     */
    boolean postNewCommentForArticle(Comment newComment);
}
