package edu.rorke.blog.background.management.entity.dto;

import edu.rorke.blog.background.management.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/6 21:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private int articleId;
    private String title;
    private String description;
    private LocalDate publishDate;
    private String articleContent;
    private int attributeId;
    private List<Tag> tagList;
    private int clickNum;
    private int likeNum;
    private int commentNum;
}
