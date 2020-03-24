package edu.rorke.blog.background.management.service.impl;

import edu.rorke.blog.background.management.entity.Comment;
import edu.rorke.blog.background.management.repository.CommentDao;
import edu.rorke.blog.background.management.service.CommentListService;
import edu.rorke.blog.background.management.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/3/15 15:38
 */
@Service
public class CommentListServiceImpl implements CommentListService {
    private CommentDao dao;

    @Autowired
    public CommentListServiceImpl(CommentDao dao) {
        this.dao = dao;
    }

    @Override
    public Page<Comment> getCommentPaginationList(Integer articleId,Integer page,Integer limit) {
        return dao.findAllByArticleId(articleId, PaginationUtil.defaultPageRequest(page,limit));
    }

    @Override
    public Boolean deleteComment(Integer commentId) {
        Optional<Comment> comment = dao.findById(commentId);
        comment.ifPresent(comment1 -> dao.delete(comment1));
        return true;
    }

    @Override
    public Boolean showComment(Integer commentId) {
        Optional<Comment> comment = dao.findById(commentId);
        comment.ifPresent(comment1 -> {
            int tmp = comment1.getShow()+1%2;
            comment1.setShow(tmp);
            dao.saveAndFlush(comment1);
        });
        return null;
    }
}
