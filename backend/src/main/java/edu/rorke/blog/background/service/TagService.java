package edu.rorke.blog.background.service;


import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/11 12:22
 */
public interface TagService {
    /**
     * 获得分页的标签
     * @param page 页数
     * @param pageSize 页面大小
     * @return 标签的分页
     */
    Page<Tag> getPaginationTags(int page, int pageSize);

    /**
     * 更新标签的内容
     * @param tagId
     * @param newTag
     * @return
     */
    Tag updateTag(int tagId, Tag newTag);

    /**
     * 获得标签相关的文章简要信息列表
     * @param tagId 标签id
     * @return 文章简要信息列表
     */
    List<ArticleInfo> getRelativeArticleInfo(int tagId);
}
