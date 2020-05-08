package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.Comment;
import edu.rorke.blog.background.repository.*;
import edu.rorke.blog.background.service.CommentService;
import edu.rorke.blog.background.util.CacheUtil;
import edu.rorke.blog.background.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/5/4 0:21
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final ArticleInfoDao articleInfoDao;
    private final RedisTemplate<String, Serializable> redisTemplate;
    private final TagDao tagDao;
    private final ArticleAndTagDao articleAndTagDao;
    private final AttributeDao attributeDao;

    public CommentServiceImpl(CommentDao commentDao,
                              ArticleInfoDao articleInfoDao,
                              RedisTemplate<String, Serializable> redisTemplate,
                              TagDao tagDao,
                              ArticleAndTagDao articleAndTagDao,
                              AttributeDao attributeDao) {
        this.commentDao = commentDao;
        this.articleInfoDao = articleInfoDao;
        this.redisTemplate = redisTemplate;
        this.tagDao = tagDao;
        this.articleAndTagDao = articleAndTagDao;
        this.attributeDao = attributeDao;
    }

    @Override
    public List<Comment> getCommentsOfArticle(int articleId) {
        return commentDao.findByArticleId(articleId,Sort.by(Sort.Direction.ASC,"publishDate"));
    }

    @Override
    public boolean postNewCommentForArticle(Comment newComment) {
        int articleId = newComment.getArticleId();
        Optional<ArticleInfo> notNull = articleInfoDao.findById(articleId);
        notNull.ifPresent(articleInfo->{
            int commentNum = articleInfo.getCommentNum()+1;
            articleInfo.setCommentNum(commentNum);
            articleInfoDao.save(articleInfo);
            commentDao.save(newComment);
            CacheUtil.updateRecommend(articleInfo,redisTemplate,tagDao,articleAndTagDao,attributeDao);
        });
        return notNull.isPresent();
    }
}
