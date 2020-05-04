package edu.rorke.blog.background.service;

import edu.rorke.blog.background.entity.oauth.OauthUser;

import java.net.SocketException;

/**
 * @author Rorke
 * @date 2020/4/30 13:46
 */
public interface OauthService {
    /**
     * 发送post请求以获得access token
     * @param url 授权认证服务器
     * @param code 授权第一阶段的code
     * @param clientId 我的授权服务器id
     * @param clientSecret 授权服务器加密规则
     * @return access token
     */
    String getAccessToken(String url,String code,String clientId,String clientSecret);

    /**
     * 根据token获取用户信息
     * @param url 获取用户信息的url
     * @param accessToken token
     * @return 用户信息
     */
    OauthUser getUserInfoFromToken(String url, String accessToken) ;
}
