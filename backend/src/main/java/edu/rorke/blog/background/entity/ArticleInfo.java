package edu.rorke.blog.background.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/6 13:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_article_info")
@EntityListeners(AuditingEntityListener.class)
public class ArticleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    private String title;
    private String description;
    @CreatedDate
    private LocalDate publishDate;
    @LastModifiedDate
    private LocalDate lastUpdate;
    private int clickNum;
    private int likeNum;
    private int commentNum;
    private int attributeId;
    private int isTop;
    private int isDelete;
    @Transient
    private List<Tag> tagList;
    @Transient
    private String attributeName;
}
