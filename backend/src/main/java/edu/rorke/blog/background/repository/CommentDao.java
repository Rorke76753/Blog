package edu.rorke.blog.background.repository;

import edu.rorke.blog.background.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/5/4 0:09
 */
@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {
    /**
     * 获得文章评论
     * @param articleId
     * @param sort
     * @return
     */
    List<Comment> findByArticleId(int articleId, Sort sort);
}
