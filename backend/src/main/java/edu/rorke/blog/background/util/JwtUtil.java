package edu.rorke.blog.background.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rorke
 * @date 2020/4/25 19:47
 */
@Component
public class JwtUtil {
    public static final String TOKEN_PREFIX = "Bearer ";
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expired}")
    private Long expired;

    /**
     * 后台验证生成token
     * @param userDetails 用户信息，根据RBAC构建的权限、角色、用户信息等相关数据库表
     * @return Token字符串
     */
    public String generatedToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>(3);
        claims.put("sub",userDetails.getUsername());
        claims.put("created", LocalDateTime.now());
        return generatedToken(claims);
    }

    public Boolean validateToken(String token,UserDetails details){
        String username = getUsernameFromToken(token);
        return Objects.equals(username,details.getUsername())&&!isTokenExpired(token);
    }

    public String getUsernameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * 生成Token字符串，具体的生成token的方法
     * @param claims token的相关信息
     * @return token字符串
     */
    private String generatedToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expired);
        return TOKEN_PREFIX + Jwts.builder().setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    private Boolean isTokenExpired(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            Date expired = claims.getExpiration();
            return expired.before(new Date());
        }catch (Exception e){
            return true;
        }
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }
}
