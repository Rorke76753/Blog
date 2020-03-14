package edu.rorke.blog.background.management.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Rorke
 * @date 2020/3/10 18:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_article_tag")
public class ArticleAndTag {
    @EmbeddedId
    private EmbeddedArticleAndTagKey embedded;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleAndTag)) {
            return false;
        }
        ArticleAndTag that = (ArticleAndTag) o;
        return Objects.equals(getEmbedded(), that.getEmbedded());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmbedded());
    }
}
