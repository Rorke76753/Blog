package edu.rorke.blog.background.management.service.impl;

import edu.rorke.blog.background.management.entity.ArticleInfo;
import edu.rorke.blog.background.management.entity.Attribute;
import edu.rorke.blog.background.management.repository.ArticleInfoDao;
import edu.rorke.blog.background.management.repository.AttributeDao;
import edu.rorke.blog.background.management.service.ArticlesService;
import edu.rorke.blog.background.management.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Rorke
 * @date 2020/4/6 13:54
 */
@Service
public class ArticlesServiceImpl implements ArticlesService {
    private ArticleInfoDao articleInfoDao;
    private Map<Integer,String> attributeMap = new HashMap<>();
    @Autowired
    public ArticlesServiceImpl(ArticleInfoDao articleInfoDao, AttributeDao attributeDao) {
        this.articleInfoDao = articleInfoDao;
        List<Attribute> attributes = attributeDao.findAll();
        for (Attribute attribute:attributes) {
            attributeMap.put(attribute.getAttributeId(),attribute.getAttributeName());
        }
    }

    @Override
    public Page<ArticleInfo> getPaginationArticles(int page, int pageSize) {
        Page<ArticleInfo> infos = articleInfoDao.findAll(PaginationUtil.defaultSortedPageRequest(page, pageSize,"publishDate", Sort.Direction.ASC));
        for (ArticleInfo info : infos) {
            info.setAttributeName(attributeMap.get(info.getAttributeId()));
        }
        return infos;
    }
}
