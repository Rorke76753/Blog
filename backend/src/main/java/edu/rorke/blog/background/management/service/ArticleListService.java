package edu.rorke.blog.background.management.service;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.dto.ArticleDto;
import edu.rorke.blog.background.management.error.NoSuchArticleException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Rorke
 */
public interface ArticleListService {

    /**
     * 分页查找文章
     * @param limit 页面大小
     * @param page  当前页数
     * @return      分页查询结果
     */
    Page<Article> getArticleListWithPagination(Integer page,Integer limit);

    /**
     * 获得文章的简要信息
     * @param limit 页面大小
     * @param page  当前页数
     * @return      查询结果
     */
    Page<ArticleDto> getBriefArticleListWithPagination(Integer page,Integer limit);

    /**
     * 获得所有文章
     * @return 文章列表
     */
    List<Article> getArticleList();

    /**
     * 删除多个文章
     * @param list  待删除的文章
     * @return      对应文章的删除情况
     * @throws NoSuchArticleException 找不到对应文章
     */
    Boolean deleteArticles(List<Integer> list) throws NoSuchArticleException;

    /**
     * 找出所有标题为title的文章
     * @param title 标题
     * @param limit 页面大小
     * @param page  当前页数
     * @return      所有标题为title的文章
     */
    Page<ArticleDto> findArticleByTitle(String title, int page, int limit);
}
