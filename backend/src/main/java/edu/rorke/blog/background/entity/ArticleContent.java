package edu.rorke.blog.background.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Rorke
 * @date 2020/4/6 20:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_article_content")
public class ArticleContent {
    @Id
    private int articleId;
    private String articleContent;
}
