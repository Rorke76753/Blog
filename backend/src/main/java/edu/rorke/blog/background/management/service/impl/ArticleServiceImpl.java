package edu.rorke.blog.background.management.service.impl;

import edu.rorke.blog.background.management.entity.ArticleContent;
import edu.rorke.blog.background.management.entity.ArticleInfo;
import edu.rorke.blog.background.management.entity.Attribute;
import edu.rorke.blog.background.management.repository.ArticleContentDao;
import edu.rorke.blog.background.management.repository.ArticleInfoDao;
import edu.rorke.blog.background.management.repository.AttributeDao;
import edu.rorke.blog.background.management.service.ArticleService;
import edu.rorke.blog.background.management.util.ArticleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/4/6 15:32
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleInfoDao articleInfoDao;
    private ArticleContentDao articleContentDao;
    private AttributeDao attributeDao;
    private ArticleUtil articleUtil;

    @Autowired
    public ArticleServiceImpl(ArticleUtil articleUtil,
                              AttributeDao attributeDao,
                              ArticleInfoDao articleInfoDao,
                              ArticleContentDao articleContentDao) {
        this.articleUtil = articleUtil;
        this.attributeDao = attributeDao;
        this.articleInfoDao = articleInfoDao;
        this.articleContentDao = articleContentDao;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean saveNewArticle(ArticleInfo articleInfo, ArticleContent articleContent) {
        int id = saveNewArticleInfo(articleInfo);
        articleContent.setArticleId(id);
        articleUtil.saveTags(id,articleInfo.getTagList());
        articleUtil.modifyAttributeRelativeNum(articleInfo.getAttributeId());
        return saveNewArticleContent(articleContent);
    }

    private Integer saveNewArticleInfo(ArticleInfo articleInfo){
        return articleInfoDao.save(articleInfo).getArticleId();
    }

    private Boolean saveNewArticleContent(ArticleContent articleContent){
        articleContentDao.save(articleContent);
        return true;
    }
}
