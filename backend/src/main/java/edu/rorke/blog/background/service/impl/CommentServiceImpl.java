package edu.rorke.blog.background.service.impl;

import edu.rorke.blog.background.entity.ArticleInfo;
import edu.rorke.blog.background.entity.Comment;
import edu.rorke.blog.background.repository.ArticleInfoDao;
import edu.rorke.blog.background.repository.CommentDao;
import edu.rorke.blog.background.service.CommentService;
import edu.rorke.blog.background.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/5/4 0:21
 */
@Service
public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;
    private ArticleInfoDao articleInfoDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, ArticleInfoDao articleInfoDao) {
        this.commentDao = commentDao;
        this.articleInfoDao = articleInfoDao;
    }

    @Override
    public Page<Comment> getCommentsOfArticle(int articleId, int page, int pageSize) {
        return commentDao.findByArticleId(articleId, PaginationUtil.defaultSortedPageRequest(page, pageSize, "publishDate", Sort.Direction.ASC));
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
        });
        return notNull.isPresent();
    }
}
