package edu.rorke.blog.background.controller.front;

import edu.rorke.blog.background.entity.Attribute;
import edu.rorke.blog.background.service.AttributeService;
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
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping
    public List<Attribute> getAllAttributes(){
        return attributeService.getAttributeList();
    }
}
