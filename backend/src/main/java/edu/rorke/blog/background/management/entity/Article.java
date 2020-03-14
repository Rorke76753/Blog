package edu.rorke.blog.background.management.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Rorke
 * @date 2020/3/6 20:34
 */
@Data
@Entity
@Table(name = "tb_article")
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int coverId;
    private String title;
    private String content;
    private String description;
    private String attribute;
    private int clickNum;
    private int commentNum;
    private int likeNum;
    private int top;
    @CreatedDate
    private Date publishDate;
    @LastModifiedDate
    private Date lastUpdate;
    @Transient
    private List<Tag> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        Article article = (Article) o;
        return getId() == article.getId() &&
                getCoverId() == article.getCoverId() &&
                getClickNum() == article.getClickNum() &&
                getCommentNum() == article.getCommentNum() &&
                getLikeNum() == article.getLikeNum() &&
                getTop() == article.getTop() &&
                Objects.equals(getTitle(), article.getTitle()) &&
                Objects.equals(getContent(), article.getContent()) &&
                Objects.equals(getDescription(), article.getDescription()) &&
                Objects.equals(getPublishDate(), article.getPublishDate()) &&
                Objects.equals(getLastUpdate(), article.getLastUpdate()) &&
                Objects.equals(getTags(), article.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCoverId(), getTitle(), getContent(), getDescription(), getClickNum(), getCommentNum(), getLikeNum(), getTop(), getPublishDate(), getLastUpdate(), getTags());
    }
}
