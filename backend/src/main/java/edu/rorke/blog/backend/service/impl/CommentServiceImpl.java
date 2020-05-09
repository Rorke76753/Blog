package edu.rorke.blog.backend.service.impl;

import edu.rorke.blog.backend.entity.ArticleInfo;
import edu.rorke.blog.backend.entity.Comment;
import edu.rorke.blog.backend.repository.*;
import edu.rorke.blog.backend.service.CommentService;
import edu.rorke.blog.backend.util.CacheUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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
    public Comment postNewCommentForArticle(Comment newComment) {
        int articleId = newComment.getArticleId();
        Comment comment = null;
        Optional<ArticleInfo> notNull = articleInfoDao.findById(articleId);
        if(notNull.isPresent()){
            ArticleInfo articleInfo = notNull.get();
            int commentNum = articleInfo.getCommentNum()+1;
            articleInfo.setCommentNum(commentNum);
            articleInfoDao.save(articleInfo);
            comment = commentDao.save(newComment);
            CacheUtil.updateRecommend(articleInfo,redisTemplate,tagDao,articleAndTagDao,attributeDao);
        }
        return comment;
    }
}
