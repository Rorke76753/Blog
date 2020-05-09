package edu.rorke.blog.backend.repository;

import edu.rorke.blog.backend.entity.BlogAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/4/29 17:01
 */
@Repository
public interface BlogAuthenticationDao extends JpaRepository<BlogAuthentication,Integer> {
    /**
     * 根据用户名寻找用户信息
     * @param username 用户名
     * @return 账户
     */
    Optional<BlogAuthentication> findByUsername(String username);
}
