package edu.rorke.blog.background.management.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Rorke
 * @date 2020/3/7 14:13
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    @CreatedDate
    private Date publishDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) o;
        return getId() == tag.getId() &&
                Objects.equals(getContent(), tag.getContent()) &&
                Objects.equals(getPublishDate(), tag.getPublishDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent(), getPublishDate());
    }
}
