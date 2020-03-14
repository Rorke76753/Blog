package edu.rorke.blog.background.management.service;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.error.NoSuchArticleException;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
public interface ArticleService {
    /**
     * 保存文章方法，分为三个步骤保存
     * 1.保存标签
     * 2.保存文章
     * 3.保存标签和文章的关系
     * @param article 文章
     * @param tagList 保存后的标签列表
     * @return        这个文章的标签数
     */
    Integer saveArticle(Article article, List<Tag> tagList);

    /**
     * 删除单个文章
     * @param id 文章id
     * @return   删除是否成功
     * @throws NoSuchArticleException 没有这个文章异常
     */
    Boolean deleteArticle(Integer id) throws NoSuchArticleException;

    /**
     * 找到文章id为articleId的文章
     * @param articleId 文章id
     * @return          文章
     * @throws NoSuchArticleException 没有这个文章异常
     */
    Article findArticleById(Integer articleId) throws NoSuchArticleException;
}
