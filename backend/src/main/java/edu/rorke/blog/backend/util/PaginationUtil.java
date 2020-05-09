package edu.rorke.blog.backend.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Rorke
 * @date 2020/3/14 11:07
 */
public class PaginationUtil {
    /**
     * 分页工具
     * @param page 页数
     * @param pageSize 每页大小
     * @param sortBy 根据什么字段排序
     * @param sortDirection 排序的方向
     * @return 分页
     */
    public static PageRequest defaultSortedPageRequest(int page, int pageSize,String sortBy,Sort.Direction sortDirection) {
        return PageRequest.of(page-1, pageSize, Sort.by(sortDirection, sortBy));
    }
}
