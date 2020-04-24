package edu.rorke.blog.background.entity.dto;

import edu.rorke.blog.background.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/6 21:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article extends ClientInfo{
    private int articleId;
    private String title;
    private String description;
    private String articleContent;
    private int attributeId;
    private List<Tag> tagList;
}
