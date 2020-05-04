package edu.rorke.blog.background.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/5/4 0:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDynamicSearch {
    private int articleId;
    private int page;
    private int pageSize;
}
