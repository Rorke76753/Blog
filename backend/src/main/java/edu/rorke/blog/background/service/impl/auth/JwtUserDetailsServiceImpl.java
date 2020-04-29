package edu.rorke.blog.background.service.impl.auth;

import edu.rorke.blog.background.entity.BlogAuthentication;
import edu.rorke.blog.background.repository.BlogAuthenticationDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Rorke
 * @date 2020/4/27 15:14
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private final BlogAuthenticationDao userDao;

    public JwtUserDetailsServiceImpl(BlogAuthenticationDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在数据库中获得用户数据
        Optional<BlogAuthentication> nullable = userDao.findByUsername(username);
        if(!nullable.isPresent()){
            throw new UsernameNotFoundException("user "+username+" not fount");
        }
        BlogAuthentication user = nullable.get();

        //创建UserDetails并返回
        return  new MyUserDetailsImpl( user.getPassword(),
                                       user.getUsername(),
                                       null);
    }

}
