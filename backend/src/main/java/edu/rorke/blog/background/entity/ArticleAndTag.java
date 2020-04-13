package edu.rorke.blog.background.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Rorke
 * @date 2020/4/6 15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_article_tag")
public class ArticleAndTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int atId;
    @Column(name = "article_id")
    private int articleId;
    @Column(name = "tag_id")
    private int tagId;
}
