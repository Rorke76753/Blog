package edu.rorke.blog.background.service;

import edu.rorke.blog.background.entity.Comment;
import org.springframework.data.domain.Page;

/**
 * @author Rorke
 * @date 2020/5/4 0:10
 */
public interface CommentService {
    /**
     * 获得某一文章下的评论列表
     * @param articleId 文章id
     * @param page 页数
     * @param pageSize 页面大小
     * @return 评论列表（分页）
     */
    Page<Comment> getCommentsOfArticle(int articleId,int page,int pageSize);

    /**
     * 增加新的评论
     * @param newComment
     * @return
     */
    boolean postNewCommentForArticle(Comment newComment);
}
