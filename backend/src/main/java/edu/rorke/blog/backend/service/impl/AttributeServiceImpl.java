package edu.rorke.blog.backend.service.impl;

import edu.rorke.blog.backend.entity.Attribute;
import edu.rorke.blog.backend.repository.AttributeDao;
import edu.rorke.blog.backend.service.AttributeService;
import edu.rorke.blog.backend.util.ArticleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/10 17:51
 */
@Service
public class AttributeServiceImpl implements AttributeService {
    private final AttributeDao attributeDao;
    private List<Attribute> attributeList;

    @Autowired
    public AttributeServiceImpl(AttributeDao attributeDao) {
        this.attributeDao = attributeDao;
    }


    @Override
    public List<Attribute> getAttributeList() {
        if(attributeList==null) {
            attributeList = attributeDao.findAll();
            HashMap<Integer,String> map = new HashMap<>(3);
            for (Attribute attribute :
                    attributeList) {
                map.put(attribute.getAttributeId(),attribute.getAttributeName());
            }
            ArticleUtil.setAttributeMap(map);
        }
        return attributeList;
    }
}
