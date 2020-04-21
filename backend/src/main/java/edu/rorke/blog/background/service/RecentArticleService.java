package edu.rorke.blog.background.service;

import edu.rorke.blog.background.entity.ArticleInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
