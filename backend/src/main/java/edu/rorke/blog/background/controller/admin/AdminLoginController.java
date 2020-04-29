package edu.rorke.blog.background.controller.admin;

import edu.rorke.blog.background.service.impl.auth.JwtUserDetailsServiceImpl;
import edu.rorke.blog.background.util.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rorke
 * @date 2020/4/29 19:58
 */

@RestController
@RequestMapping("/api/login/validate")
public class AdminLoginController {
    private final JwtUtil jwtUtil;
    private final JwtUserDetailsServiceImpl userDetailsService;

    public AdminLoginController(JwtUtil jwtUtil, JwtUserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public Boolean isTokenExpired(@RequestParam("token") String token){
        if(token.startsWith(JwtUtil.TOKEN_PREFIX)) {
            token = token.substring(7);
            String username = jwtUtil.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return userDetails != null && jwtUtil.validateToken(token, userDetails);
        }
        return false;
    }
}
