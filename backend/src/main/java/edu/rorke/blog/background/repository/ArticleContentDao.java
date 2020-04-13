package edu.rorke.blog.background.repository;

import edu.rorke.blog.background.entity.ArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rorke
 * @date 2020/4/6 21:02
 */
public interface ArticleContentDao extends JpaRepository<ArticleContent,Integer> {
}
