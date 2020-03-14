package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/3/7 13:26
 */

public interface ArticleDao extends JpaRepository<Article,Integer> {
    /**
     * 根据标题寻找所有的文章
     * @param title  标题
     * @param pageable 分页
     * @return       文章列表
     */
    Page<Article> findByTitleLike(String title,Pageable pageable);

}
