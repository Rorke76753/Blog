package edu.rorke.blog.backend.entity.dto;

import edu.rorke.blog.backend.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/6 21:02
 */
@EqualsAndHashCode(callSuper = true)
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
