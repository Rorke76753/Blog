package edu.rorke.blog.background.management.service.impl;

import edu.rorke.blog.background.management.entity.Article;
import edu.rorke.blog.background.management.entity.dto.ArticleDto;
import edu.rorke.blog.background.management.repository.ArticleDao;
import edu.rorke.blog.background.management.service.ArticleListService;
import edu.rorke.blog.background.management.service.impl.util.ArticleUtil;
import edu.rorke.blog.background.management.service.impl.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/3/11 15:20
 */
@Service
public class ArticleListServiceImpl implements ArticleListService {
    private ArticleDao articleDao;

    @Autowired
    public ArticleListServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public Page<Article> getArticlePaginationList(Integer page, Integer limit) {
        return articleDao.findAll(PaginationUtil.defaultPageRequest(page, limit));
    }

    @Override
    public Page<ArticleDto> getBriefArticlePaginationList(Integer page, Integer limit) {
        Page<Article> articles = getArticlePaginationList(page, limit);
        List<ArticleDto> dtoList = ArticleUtil.convertArticleToDto(articles);
        return new PageImpl<>(dtoList);
    }

    @Override
    public List<Article> getArticleList() {
        return articleDao.findAll();
    }

    @Override
    public Boolean deleteArticles(List<Integer> list){
        for (Integer integer : list) {
            Article article = ArticleUtil.findArticleById(articleDao, integer);
            articleDao.delete(article);
        }
        return Boolean.TRUE;
    }

    @Override
    public Page<ArticleDto> getBriefArticlePaginationListByTitle(String title, int page, int limit) {
        Page<Article> articles = articleDao.findByTitleLike(title, PaginationUtil.defaultPageRequest(page, limit));
        List<ArticleDto> dtoList = ArticleUtil.convertArticleToDto(articles);
        return new PageImpl<>(dtoList);
    }
}