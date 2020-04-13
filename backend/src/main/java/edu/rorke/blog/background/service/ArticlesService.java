package edu.rorke.blog.background.service;

import edu.rorke.blog.background.entity.ArticleInfo;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

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
    @Deprecated
    Page<ArticleInfo> getPaginationArticleInfo(int page, int pageSize);

    /**
     * 多删除
     * @param articleIds 文章id
     * @return 成功删除得id
     */
    Integer[] deleteMultipleById(Integer[] articleIds);

    /**
     * 根据条件实现动态查询
     * @param title 标题
     * @param attributeId 属性id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页数
     * @param pageSize 页面大小
     * @return 分页结果
     */
    Page<ArticleInfo> dynamicSearch(String title, int attributeId, LocalDate startDate, LocalDate endDate, int page, int pageSize);

}
