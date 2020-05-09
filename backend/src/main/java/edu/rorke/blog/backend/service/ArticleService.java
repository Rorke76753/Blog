package edu.rorke.blog.backend.service;

import edu.rorke.blog.backend.entity.ArticleContent;
import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.dto.Article;

/**
 * @author Rorke
 * @date 2020/4/6 15:31
 */
public interface ArticleService {
    /**
     * 保存新增的文章
     * @param articleInfo 文章
     * @param articleContent 文章内容
     * @return 保存是否成功
     */
    Boolean saveNewArticle(ArticleInfo articleInfo, ArticleContent articleContent);

    /**
     * 根据文章id获得文章内容
     * @param articleId 文章id
     * @return 文章内容
     */
    ArticleContent getArticleContentById(int articleId);

    /**
     * 更新文章
     * @param articleId 文章id
     * @param article 前端传来的格式
     * @return 成功与否
     */
    Boolean updateArticle(int articleId, Article article);

    /**
     * 根据id删除，就是把isDelete置为1
     * @param articleId 文章id
     */
    void deleteByArticleId(int articleId);

    /**
     * 获得某一个文章的信息
     * @param articleId 获得文章信息
     * @return 文章信息
     */
    ArticleInfo getArticleInfoById(int articleId);
}
