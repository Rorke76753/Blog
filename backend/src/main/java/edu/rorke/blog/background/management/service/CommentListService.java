package edu.rorke.blog.background.management.service;

import edu.rorke.blog.background.management.entity.Comment;
import org.springframework.data.domain.Page;

/**
 * @author Rorke
 * @date 2020/3/15 15:36
 */
public interface CommentListService {
    /**
     * 获得评论的分页列表
     * @param articleId 文章id
     * @param page      当前页数
     * @param limit     页面大小
     * @return  评论的分页列表
     */
    Page<Comment> getCommentPaginationList(Integer articleId, Integer page, Integer limit);

    /**
     * 删除id为commentId的评论
     * @param commentId 评论id
     * @return
     */
    Boolean deleteComment(Integer commentId);

    /**
     * 是否显示评论，只要调用这个方法，显示的状态会取反
     * @param commentId 评论id
     * @return          评论不存在的时候会抛出异常，存在的时候永远为true
     */
    Boolean showComment(Integer commentId);
}
