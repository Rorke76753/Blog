package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rorke
 */
public interface CommentDao extends JpaRepository<Comment,Integer> {
    /**
     * 找到文章下的所有评论
     * @param articleId 文章id
     * @param pageable  分页
     * @return          评论列表
     */
    Page<Comment> findAllByArticleId(Integer articleId, Pageable pageable);
}
