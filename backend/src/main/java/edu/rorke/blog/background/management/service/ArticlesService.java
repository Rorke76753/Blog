package edu.rorke.blog.background.management.service;

import edu.rorke.blog.background.management.entity.ArticleInfo;
import org.springframework.data.domain.Page;

/**
 * @author Rorke
 * @date 2020/4/6 13:53
 */
public interface ArticlesService {
    /**
     * 分页获得文章列表
     * @param page  页数
     * @param pageSize  每一页的大小
     * @return  文章dto分页列表
     */
    Page<ArticleInfo> getPaginationArticles(int page, int pageSize);
}
