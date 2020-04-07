package edu.rorke.blog.background.management.util;

import edu.rorke.blog.background.management.entity.ArticleAndTag;
import edu.rorke.blog.background.management.entity.Attribute;
import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.repository.ArticleAndTagDao;
import edu.rorke.blog.background.management.repository.AttributeDao;
import edu.rorke.blog.background.management.repository.TagDao;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Rorke
 * @date 2020/3/10 18:45
 */
@Component
public class ArticleUtil {
    private TagDao tagDao;
    private AttributeDao attributeDao;
    private ArticleAndTagDao articleAndTagDao;

    @Autowired
    public ArticleUtil(TagDao tagDao, AttributeDao attributeDao, ArticleAndTagDao articleAndTagDao) {
        this.tagDao = tagDao;
        this.attributeDao = attributeDao;
        this.articleAndTagDao = articleAndTagDao;
    }

    /**
     * 保存文章的标签
     * 首先先寻找标签和文章的关系表中关于文章id的关系是否存在
     * 如果关系不存在，就直接把文章与标签的关系保存起来。
     * 具体地，就是查看标签列表中每一个标签是否存在在数据库中
     * 如果标签存在，把它的相关联的数量加1
     * 否则直接存进标签表中，最后保存文章与标签的关系。
     *
     * 相反地，如果关系存在则先将标签表中的标签分类。
     * 首先使用{@code Set<String>}存放旧关系中的标签名称
     * 如果新的{@code List<Tag>}中的元素标签名{@code tagContent}存在于{@code Set<String>}中，
     * 则两个都同时除掉，因为这表示在旧关系中这个文章与标签的关系已经存在了。
     * 所以删到最后，{@code Set<String>}中的就是新关系中没有出现的，应该删除掉
     * 而{@code List<Tag>}中的就是新关系新增的，应该保存起来
     * @param articleId 文章id
     * @param tagList 标签列表
     */
    public void saveTags(int articleId, List<Tag> tagList) {
        List<ArticleAndTag> relativeList = articleAndTagDao.findAllByArticleId(articleId);
        if (!relativeList.isEmpty()) {
            Set<String> tagContentSet = new HashSet<>();
            for (ArticleAndTag relation : relativeList) {
                Tag tag = tagDao.findByTagId(relation.getTagId());
                tagContentSet.add(tag.getTagContent());
            }
            for (Tag tag : tagList) {
                if (tagContentSet.contains(tag.getTagContent())) {
                    tagList.remove(tag);
                    tagContentSet.remove(tag.getTagContent());
                }
            }
            List<ArticleAndTag> newRelation = new ArrayList<>();
            for (Tag tag : tagList) {
                Optional<Tag> nullable = tagDao.findByTagContent(tag.getTagContent());
                Tag varTag = nullable.orElse(Tag.builder().tagContent(tag.getTagContent()).relativeNum(0).build());
                varTag.setRelativeNum(varTag.getRelativeNum()+1);
                int tagId = tagDao.save(varTag).getTagId();
                ArticleAndTag relation = ArticleAndTag.builder().articleId(articleId).tagId(tagId).build();
                newRelation.add(relation);
            }
            articleAndTagDao.saveAll(newRelation);
            for (String shouldBeDeleted : tagContentSet) {
                Optional<Tag> notNull = tagDao.findByTagContent(shouldBeDeleted);
                notNull.ifPresent(tag -> {
                    int relativeNum = tag.getRelativeNum()-1;
                    int tagId = tag.getTagId();
                    tag.setRelativeNum(relativeNum);
                    tagDao.save(tag);
                    articleAndTagDao.deleteByArticleIdAndTagId(articleId,tagId);
                });
            }
        } else {
            for (Tag tag : tagList) {
                Optional<Tag> nullable = tagDao.findByTagContent(tag.getTagContent());
                int tagId;
                if (nullable.isPresent()) {
                    Tag varTag = nullable.get();
                    varTag.setRelativeNum(varTag.getRelativeNum() + 1);
                    tagId = tagDao.save(varTag).getTagId();
                } else {
                    int relativeNum = tag.getRelativeNum()+1;
                    tag.setRelativeNum(relativeNum);
                    tagId = tagDao.save(tag).getTagId();
                }
                ArticleAndTag at = ArticleAndTag.builder().articleId(articleId).tagId(tagId).build();
                articleAndTagDao.save(at);
            }
        }
    }

    /**
     * 修正属性相关的文章数目
     * @param attributeId 属性id
     */
    public void modifyAttributeRelativeNum(int attributeId) {
        Optional<Attribute> notNullResult = attributeDao.findById(attributeId);
        notNullResult.ifPresent(attribute -> {
            int count = attribute.getRelativeNum() + 1;
            attribute.setRelativeNum(count);
            attributeDao.save(attribute);
        });
    }
}
