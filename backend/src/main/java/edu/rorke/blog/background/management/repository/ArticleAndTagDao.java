package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.ArticleAndTag;
import edu.rorke.blog.background.management.entity.EmbeddedArticleAndTagKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Rorke
 */
@Repository
public interface ArticleAndTagDao extends JpaRepository<ArticleAndTag, EmbeddedArticleAndTagKey> {
    /**
     * 根据文章寻找对应的所有标签
     * @param articleId 文章id
     * @return          文章与标签的关系
     */
    List<ArticleAndTag> findByEmbeddedArticleId(Integer articleId);

    /**
     * 根据标签寻找对应的文章
     * @param tagId 标签id
     * @return      文章与标签的关系
     */
    List<ArticleAndTag> findByEmbeddedTagId(Integer tagId);
}
