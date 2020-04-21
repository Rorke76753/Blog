package edu.rorke.blog.background.service;

import edu.rorke.blog.background.entity.ArticleInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/4/18 13:24
 */
public interface ClickService {
    /**
     * 统计点击量，将访问的ip地址放到redis中
     * @param articleId 文章id
     * @param ipAddress 请求访问的ip地址
     */
    void countClickNums(int articleId,String ipAddress);


}
