package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.ArticleAndTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/6 22:13
 */
@Repository
public interface ArticleAndTagDao extends JpaRepository<ArticleAndTag,Integer> {
    /**
     * 根据文章id寻找文章与标签的链接
     * @param articleId 文章id
     * @return  文章id与标签的链接
     */
    List<ArticleAndTag> findAllByArticleId(int articleId);

    /**
     * 根据文章和标签id删除
     * @param articleId 文章id
     * @param tagId 标签id
     */
    void deleteByArticleIdAndTagId(int articleId,int tagId);
}
