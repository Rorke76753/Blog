package edu.rorke.blog.background.config;

import edu.rorke.blog.background.filter.JwtAuthenticationTokenFilter;
import edu.rorke.blog.background.service.impl.auth.JwtAuthenticationEntryPoint;
import edu.rorke.blog.background.service.impl.auth.JwtUserDetailsServiceImpl;
import edu.rorke.blog.background.util.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Rorke
 * @date 2020/4/27 15:06
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtUserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtAuthenticationEntryPoint entryPoint,
                          JwtUserDetailsServiceImpl userDetailsService,
                          JwtUtil jwtUtil)  {
        this.entryPoint = entryPoint;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/login/**").permitAll()
                //注意！！！这里只能写作request而非其他如httpServletRequest等
                //.antMatchers("/api/admin/**").access("@rbacService.hasPermission(request,authentication)")
                .antMatchers("/api/admin/**").authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(this.createAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public JwtAuthenticationTokenFilter createAuthenticationFilter() throws Exception {
        return new JwtAuthenticationTokenFilter(this.authenticationManagerBean(), jwtUtil);
    }
}


