package edu.rorke.blog.background.management.service.impl.util;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.Tag;
import edu.rorke.blog.background.management.entity.ArticleAndTag;
import edu.rorke.blog.background.management.entity.EmbeddedArticleAndTagKey;
import edu.rorke.blog.background.management.entity.dto.ArticleDto;
import edu.rorke.blog.background.management.repository.ArticleAndTagDao;
import edu.rorke.blog.background.management.repository.ArticleDao;
import edu.rorke.blog.background.management.repository.TagDao;
import org.springframework.data.domain.Page;

import java.util.*;

/**
 * @author Rorke
 * @date 2020/3/10 18:45
 */
public class ArticleUtil {
    /**
     * 保存标签方法
     * 先在数据库中取出所有标签放入一个hashMap中，其中key是这个标签的内容，value则是标签的id
     * 根据这个hashMap对输入的标签设置一个初始值
     * jpa自动根据是否存在主键冲突来判断在数据库应该做的是update还是insert
     * @param tagList 标签
     * @param tagDao  标签的dao
     * @return 返回保存后的标签的列表
     */
    public static List<Tag> saveTagList(List<Tag> tagList, TagDao tagDao) {
        List<Tag> dbTagList = tagDao.findAll();
        if (!dbTagList.isEmpty()) {
            Map<String, Integer> dbTagMap = new HashMap<>(0);
            for (Tag tag : dbTagList) {
                dbTagMap.put(tag.getContent(), tag.getId());
            }
            int lastId = dbTagList.get(dbTagList.size() - 1).getId();
            int defaultId = lastId + 1;
            for (Tag tag : tagList) {
                int dbId;
                if (dbTagMap.get(tag.getContent()) == null) {
                    dbId = defaultId++;
                } else {
                    dbId = dbTagMap.get(tag.getContent());
                }
                tag.setId(dbId);
            }
        }
        return tagDao.saveAll(tagList);
    }

    /**
     * 保存文章方法
     * 对文章其余的属性进行一些初始化设置
     *
     * @param article    文章
     * @param articleDao 文章的dao
     * @return 保存后文章的id
     */
    public static Integer getArticleIdBySaving(Article article, ArticleDao articleDao) {
        if (article.getDescription().isEmpty()) {
            int descriptionLength = Math.min(article.getContent().length(), 30);
            String initDescription = article.getContent().substring(0, descriptionLength);
            article.setDescription(initDescription + "...");
        }
        article.setLikeNum(0);
        article.setClickNum(0);
        article.setCommentNum(0);
        Article dbArticle = articleDao.save(article);
        return dbArticle.getId();
    }

    /**
     * 保存文章与标签关系的方法
     * 将文章的id和标签的id建立关系并保存起来
     *
     * @param articleAndTagDao  文章与标签关系的dao
     * @param articleId 文章的id，由上面的方法得到
     * @param tagList   标签列表，由上面的方法得到
     * @return 文章与标签的列表
     */
    public static List<ArticleAndTag> saveArticleAndTag(ArticleAndTagDao articleAndTagDao, Integer articleId, List<Tag> tagList) {
        List<ArticleAndTag> atList = new ArrayList<>();
        for (Tag tag : tagList) {
            EmbeddedArticleAndTagKey embeddedArticleAndTagKey = new EmbeddedArticleAndTagKey(articleId, tag.getId());
            ArticleAndTag aAndT = new ArticleAndTag(embeddedArticleAndTagKey);
            atList.add(aAndT);
        }
        return articleAndTagDao.saveAll(atList);
    }

    public static Article findArticleById(ArticleDao dao, Integer id){
        Optional<Article> nullableResult = dao.findById(id);
        return nullableResult.orElse(null);
    }

    public static List<ArticleDto> convertArticleToDto(Page<Article> articles){
        List<Article> articleList = articles.toList();
        List<ArticleDto> dtoList = new ArrayList<>(articleList.size());
        for (Article article :
                articleList) {
            ArticleDto dto = new ArticleDto(article);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
