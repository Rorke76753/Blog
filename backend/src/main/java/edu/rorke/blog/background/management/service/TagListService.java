package edu.rorke.blog.background.management.service;

import edu.rorke.blog.background.management.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Rorke
 */
public interface TagListService {
    /**
     * 分页返回标签
     * @param page  当前页数
     * @param limit 页面大小
     * @return      查询结果
     */
    Page<Tag> getTagPaginationList(Integer page, Integer limit);

    /**
     * 获得某一篇文章的所有标签
     * @param articleId 文章的id
     * @return  标签列表
     */
    List<Tag> getArticleTags(Integer articleId);

    /**
     * 保存标签
     * @param tags 输入的标签，包含';‘进行分割的输入
     * @return  保存好的标签列表
     */
    List<Tag> saveTags(String tags);

    /**
     * 保存对某一个标签的修改
     * @param tagId 标签id
     * @param content 内容
     * @return  修改结果
     */
    Boolean saveOneTag(int tagId, String content);

    /**
     * 删除标签
     * @param tagId 标签id
     * @return  删除结果
     */
    Boolean deleteTag(int tagId);
}
