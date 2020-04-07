package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/4/6 21:52
 */
@Repository
public interface TagDao extends JpaRepository<Tag,Integer> {
    /**
     * 根据标签id寻找标签
     * @param tagId 标签id
     * @return 标签  因为关系表中的外键设置了级联操作，
     *              所以关系表中出现的标签id肯定会再标签表中出现
     */
    Tag findByTagId(int tagId);

    /**
     * 查找标签内容是否存在标签表中
     * @param tagContent 标签内容
     * @return 因为有可能这个内容的标签并不存在数据库中，
     *         因此用Optional来处理结果
     */
    Optional<Tag> findByTagContent(String tagContent);
}
