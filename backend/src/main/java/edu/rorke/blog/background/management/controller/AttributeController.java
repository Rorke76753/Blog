package edu.rorke.blog.background.management.controller;

import edu.rorke.blog.background.management.entity.Attribute;
import edu.rorke.blog.background.management.repository.AttributeDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Rorke
 * @date 2020/4/6 14:28
 */

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {
    private AttributeDao attributeDao;

    public AttributeController(AttributeDao attributeDao) {
        this.attributeDao = attributeDao;
    }

    @GetMapping
    public List<Attribute> getAllAttributes(){
        return attributeDao.findAll();
    }
}
