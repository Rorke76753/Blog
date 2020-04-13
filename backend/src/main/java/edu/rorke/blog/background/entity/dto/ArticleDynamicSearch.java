package edu.rorke.blog.background.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Rorke
 * @date 2020/4/9 11:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDynamicSearch {
    private int page;
    private int pageSize;
    private String title;
    private int attributeId;
    private LocalDate startDate;
    private LocalDate endDate;
}
