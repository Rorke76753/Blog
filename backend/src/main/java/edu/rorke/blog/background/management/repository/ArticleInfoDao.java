package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.ArticleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rorke
 * @date 2020/4/6 13:51
 */
@Repository
public interface ArticleInfoDao extends JpaRepository<ArticleInfo,Integer> {
}
