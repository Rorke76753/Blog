package edu.rorke.blog.background.management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Rorke
 * @date 2020/3/14 19:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int articleId;
    private String content;
    private String username;
    private String emailAddress;
    private Date publishDate;
    private int deleted;
}
