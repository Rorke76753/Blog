package edu.rorke.blog.background.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Rorke
 * @date 2020/5/4 0:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInfo {
    private LocalDateTime publishDate;
    private String username;
    private String htmlUrl;
    private String avatarUrl;
    private String platform;
    private String commentContent;
    private String errorMessage;
}
