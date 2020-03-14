package edu.rorke.blog.background.management.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Rorke
 * @date 2020/3/10 18:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmbeddedArticleAndTagKey implements Serializable {
    private int articleId;
    private int tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmbeddedArticleAndTagKey)) {
            return false;
        }
        EmbeddedArticleAndTagKey that = (EmbeddedArticleAndTagKey) o;
        return getArticleId() == that.getArticleId() &&
                getTagId() == that.getTagId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleId(), getTagId());
    }
}
