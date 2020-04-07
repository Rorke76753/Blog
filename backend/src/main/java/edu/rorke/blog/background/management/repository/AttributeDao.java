package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rorke
 * @date 2020/4/6 14:14
 */
public interface AttributeDao extends JpaRepository<Attribute,Integer> {

}
