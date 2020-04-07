package edu.rorke.blog.background.management.service;

import edu.rorke.blog.background.management.entity.ArticleContent;
import edu.rorke.blog.background.management.entity.ArticleInfo;

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
}
