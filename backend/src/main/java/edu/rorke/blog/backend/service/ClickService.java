package edu.rorke.blog.backend.service;

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

    /**
     * 删除文章时应该也删除相关的点击记录
     * @param articleId
     */
    void deleteClickList(int articleId);
}
