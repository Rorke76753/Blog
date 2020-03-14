package edu.rorke.blog.background.management.repository;

import edu.rorke.blog.background.management.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rorke
 * @date 2020/3/10 18:08
 */
@Repository
public interface TagDao extends JpaRepository<Tag,Integer> {
}
