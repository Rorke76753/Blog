package edu.rorke.blog.backend.service;

import edu.rorke.blog.backend.entity.ArticleInfo;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/20 23:00
 */
public interface RecommendService {
    /**
     * 获得推荐文章列表
     * @return 推荐列表
     */
    List<ArticleInfo> getRecommendList();
}
