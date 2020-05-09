package edu.rorke.blog.backend.service;

import edu.rorke.blog.backend.entity.ArticleInfo;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

/**
 * @author Rorke
 * @date 2020/4/6 13:53
 */
public interface ArticleListService {

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
     * @param orderBy
     * @return 分页结果
     */
    Page<ArticleInfo> dynamicSearch(String title, Integer attributeId, LocalDate startDate, LocalDate endDate, int page, int pageSize, String orderBy);
}
