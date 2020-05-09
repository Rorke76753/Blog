package edu.rorke.blog.backend.controller.front;

import edu.rorke.blog.backend.entity.Attribute;
import edu.rorke.blog.backend.service.AttributeService;
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
public class AttributeListController {
    private final AttributeService attributeService;

    public AttributeListController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping
    public List<Attribute> getAllAttributes(){
        return attributeService.getAttributeList();
    }
}
