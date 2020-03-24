package edu.rorke.blog.background.management.entity.dto;

import edu.rorke.blog.background.management.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Rorke
 * @date 2020/3/13 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto{
    @Id
    private int id;
    private String title;
    private String description;
    private String attribute;
    private int top;
    private Date publishDate;
    private Date lastUpdate;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.description = article.getDescription();
        this.top = article.getTop();
        this.publishDate = article.getPublishDate();
        this.lastUpdate = article.getLastUpdate();
        this.attribute = article.getAttribute();
    }
}
