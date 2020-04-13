package edu.rorke.blog.background.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/4/11 14:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagPagination {
    private int page;
    private int pageSize;
}
