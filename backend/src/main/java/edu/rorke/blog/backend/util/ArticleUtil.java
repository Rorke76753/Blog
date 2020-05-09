package edu.rorke.blog.backend.util;

import edu.rorke.blog.backend.entity.ArticleAndTag;
import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.Attribute;
import edu.rorke.blog.backend.entity.Tag;
import edu.rorke.blog.backend.repository.ArticleAndTagDao;
import edu.rorke.blog.backend.repository.AttributeDao;
import edu.rorke.blog.backend.repository.TagDao;

import java.util.*;

/**
 * @author Rorke
 * @date 2020/3/10 18:45
 */
public class ArticleUtil {
    private static Map<Integer, String> attributeMap;

    /**
     * 保存文章的标签
     * 首先先寻找标签和文章的关系表中关于文章id的关系是否存在
     * 如果关系不存在，就直接把文章与标签的关系保存起来。
     * 具体地，就是查看标签列表中每一个标签是否存在在数据库中
     * 如果标签存在，把它的相关联的数量加1
     * 否则直接存进标签表中，最后保存文章与标签的关系。
     *
     * <p>
     * 相反地，如果关系存在则先将标签表中的标签分类。
     * 首先存放旧关系中的标签
     * 如果新的元素标签存在于旧关系中的标签中，
     * 则两个都同时除掉，因为这表示在旧关系中这个文章与标签的关系已经存在了。
     * 所以删到最后，{@code Set<Tag>}中的就是新关系中没有出现的，应该删除掉
     * 而{@code List<Tag>}中的就是新关系新增的，应该保存起来
     *
     * @param articleId 文章id
     * @param tagList   标签列表
     */
    public static void saveTags(int articleId, List<Tag> tagList, TagDao tagDao, ArticleAndTagDao articleAndTagDao) {
        List<ArticleAndTag> oldRelations = articleAndTagDao.findAllByArticleId(articleId);
        if (!oldRelations.isEmpty()) {
            Set<Tag> oldTagSet = new HashSet<>();
            for (ArticleAndTag relation : oldRelations) {
                Tag tag = tagDao.findByTagId(relation.getTagId());
                oldTagSet.add(tag);
            }
            List<Tag> oldTags = new ArrayList<>();
            for (Tag tag : tagList) {
                if (oldTagSet.contains(tag)) {
                    oldTags.add(tag);
                    oldTagSet.remove(tag);
                }
            }
            for (Tag tag : oldTags) {
                tagList.remove(tag);
            }
            deleteOldRelation(oldTagSet.iterator(), tagDao, articleAndTagDao, oldRelations);
        }
        saveNewRelation(articleId, tagList, tagDao, articleAndTagDao);
    }

    private static void saveNewRelation(int articleId, List<Tag> tagList, TagDao tagDao, ArticleAndTagDao articleAndTagDao) {
        List<ArticleAndTag> newRelation = new ArrayList<>();
        for (Tag tag : tagList) {
            Optional<Tag> nullable = tagDao.findByTagContent(tag.getTagContent());
            Tag varTag = nullable.orElse(Tag.builder().tagContent(tag.getTagContent()).relativeNum(0).build());
            int relativeNum = varTag.getRelativeNum() + 1;
            varTag.setRelativeNum(relativeNum);
            int tagId = tagDao.save(varTag).getTagId();
            ArticleAndTag relation = ArticleAndTag.builder().articleId(articleId).tagId(tagId).build();
            newRelation.add(relation);
        }
        articleAndTagDao.saveAll(newRelation);
    }

    private static void deleteOldRelation(Iterator<Tag> tags, TagDao tagDao, ArticleAndTagDao articleAndTagDao, List<ArticleAndTag> oldRelations) {
        while (tags.hasNext()) {
            Tag tag = tags.next();
            int relativeNum = tag.getRelativeNum() - 1;
            tag.setRelativeNum(relativeNum);
            if (relativeNum != 0) {
                tagDao.save(tag);
            } else {
                tagDao.delete(tag);
            }
        }
        articleAndTagDao.deleteAll(oldRelations);
    }

    /**
     * 修正属性相关的文章数目
     *
     * @param attributeId 属性id
     * @param modifyNum   1和-1
     */
    public static void modifyAttributeRelativeNum(int attributeId, int modifyNum, AttributeDao attributeDao) {
        Optional<Attribute> notNullResult = attributeDao.findById(attributeId);
        notNullResult.ifPresent(attribute -> {
            int count = attribute.getRelativeNum() + modifyNum;
            attribute.setRelativeNum(count);
            attributeDao.save(attribute);
        });
    }

    /**
     * 往{@code ArticleInfo}文章简要信息里面添加相关的标签列表
     *
     * @param articleInfo 文章简要信息
     */
    public static void appendTags(ArticleInfo articleInfo, TagDao tagDao, ArticleAndTagDao articleAndTagDao) {
        List<ArticleAndTag> relations = articleAndTagDao.findAllByArticleId(articleInfo.getArticleId());
        List<Integer> tagIds = new ArrayList<>();
        for (ArticleAndTag relation : relations) {
            tagIds.add(relation.getTagId());
        }
        List<Tag> allTag = tagDao.findAllById(tagIds);
        articleInfo.setTagList(allTag);
    }

    public static void modifyTagRelativeNum(int articleId, TagDao tagDao, ArticleAndTagDao articleAndTagDao) {
        List<ArticleAndTag> relations = articleAndTagDao.findAllByArticleId(articleId);
        List<Integer> tagIds = new ArrayList<>();
        for (ArticleAndTag relation : relations) {
            tagIds.add(relation.getTagId());
        }
        deleteOldRelation(tagDao.findAllById(tagIds).iterator(), tagDao, articleAndTagDao, relations);
    }

    public static void setAttributeMap(HashMap<Integer, String> map) {
        attributeMap = map;
    }

    public static void setAttributeName(ArticleInfo info, AttributeDao attributeDao) {
        if (attributeMap == null) {
            List<Attribute> list = attributeDao.findAll();
            attributeMap = new HashMap<>(3);
            for (Attribute attribute : list) {
                attributeMap.put(attribute.getAttributeId(), attribute.getAttributeName());
            }
        }
        info.setAttributeName(attributeMap.get(info.getAttributeId()));
    }
}
