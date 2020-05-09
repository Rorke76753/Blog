package edu.rorke.blog.backend.service.impl.auth;

import edu.rorke.blog.backend.entity.BlogAuthentication;
import edu.rorke.blog.backend.repository.BlogAuthenticationDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
