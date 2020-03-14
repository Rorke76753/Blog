package edu.rorke.blog.background.management.service.impl.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Rorke
 * @date 2020/3/14 11:07
 */
public class PaginationUtil {
    public static PageRequest defaultPageRequest(int page, int limit) {
        return PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "publishDate"));
    }
}
