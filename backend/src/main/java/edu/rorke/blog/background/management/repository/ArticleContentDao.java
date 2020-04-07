package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.ArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rorke
 * @date 2020/4/6 21:02
 */
public interface ArticleContentDao extends JpaRepository<ArticleContent,Integer> {
}
