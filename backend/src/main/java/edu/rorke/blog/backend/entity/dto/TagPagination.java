package edu.rorke.blog.backend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/4/11 14:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagPagination extends ClientInfo{
    private int page;
    private int pageSize;
    private String sortBy;
}
