package edu.rorke.blog.backend.service;


import edu.rorke.blog.backend.entity.Attribute;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/10 17:49
 */

public interface AttributeService {
    /**
     * 获得属性表
     * @return
     */
    List<Attribute> getAttributeList();
}
