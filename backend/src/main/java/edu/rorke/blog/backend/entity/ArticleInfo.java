package edu.rorke.blog.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author Rorke
 * @date 2020/4/6 13:33
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_article_info")
@EntityListeners(AuditingEntityListener.class)
public class ArticleInfo implements Serializable,Comparable<ArticleInfo> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    private String title;
    private String description;
    @CreatedDate
    private LocalDate publishDate;
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
    @Transient
    private double recommendPriority;

    public void calculatePriority(){
        double subDay = LocalDate.now().toEpochDay() - this.publishDate.toEpochDay() +1;
        DecimalFormat df =  new  DecimalFormat(  "0.00" );
        double priority =  (this.clickNum+this.likeNum+this.commentNum)/subDay;
        this.recommendPriority = Double.parseDouble(df.format(priority));
    }

    @Override
    public int compareTo(ArticleInfo o) {
        double thisPriority = this.recommendPriority;
        double oPriority = o.recommendPriority;
        while(thisPriority%1!=0||oPriority%1!=0){
            thisPriority *= 10;
            oPriority *= 10;
        }
        return (int) (oPriority-thisPriority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleInfo)) {
            return false;
        }
        ArticleInfo info = (ArticleInfo) o;
        return getArticleId() == info.getArticleId() &&
                Objects.equals(getTitle(), info.getTitle()) &&
                getPublishDate().equals(info.getPublishDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleId(), getTitle(), getPublishDate());
    }
}
