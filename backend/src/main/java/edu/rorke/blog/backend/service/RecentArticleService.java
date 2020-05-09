package edu.rorke.blog.backend.service;

import edu.rorke.blog.backend.entity.ArticleInfo;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/21 15:48
 */
public interface RecentArticleService {
    /**
     * 获得最近新增的文章列表
     * @return
     */
    List<ArticleInfo> getRecentArticleList();

}
