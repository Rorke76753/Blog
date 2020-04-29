package edu.rorke.blog.background.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.rorke.blog.background.entity.BlogAuthentication;
import edu.rorke.blog.background.service.impl.auth.MyUserDetailsImpl;
import edu.rorke.blog.background.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Rorke
 * @date 2020/4/27 15:50
 */
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(new AntPathRequestMatcher("/api/login/authentication","POST"));
        this.jwtUtil = jwtUtil;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            BlogAuthentication user = new ObjectMapper().readValue(request.getInputStream(),BlogAuthentication.class);
            String username = user.getUsername();
            String password = user.getPassword();
            return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 成功认证的动作
     * TODO:将成功信息封装成JSON返回前端
     * @param request 登录请求
     * @param response 成功认证后的返回
     * @param chain
     * @param authResult 认证结果
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MyUserDetailsImpl userDetails = (MyUserDetailsImpl) authResult.getPrincipal();
        String token = jwtUtil.generatedToken(userDetails);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.addHeader("token",token);
    }

    /**
     * 认证失败的动作
     * TODO:将失败信息封装成JSON数据返回给前端
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
