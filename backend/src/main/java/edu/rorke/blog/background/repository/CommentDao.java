package edu.rorke.blog.background.repository;

import edu.rorke.blog.background.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rorke
 * @date 2020/5/4 0:09
 */
@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {
    /**
     * 获得文章下的评论
     * @param articleId 文章id
     * @param pageable 分页
     * @return 评论列表(分页)
     */
    Page<Comment> findByArticleId(int articleId, Pageable pageable);
}
