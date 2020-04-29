package edu.rorke.blog.background.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rorke
 * @date 2020/4/28 21:07
 */

@RestController
@RequestMapping("/api")
public class GithubOauthController {
    @Value("${oauth2.github.client-id}")
    private String githubClientId;

    @Value("${oauth2.github.client-secret}")
    private String githubClientSecret;

    @Value("${oauth2.github.oauthLink}")
    private String oauthLink;

    @Value("${oauth2.github.tokenLink}")
    private String tokenLink;

    @GetMapping("/login/oauth")
    public String getGithubOauthLink(){
        return oauthLink+githubClientId;
    }

    /**
     * 前端接收callback后通知后端向认证服务器确认code
     * @param code 前端接收callback后的code
     * @return
     */
    @GetMapping("/login/oauth/callback")
    public String githubCallBack(@RequestParam("code") String code){
        String accessToken = null;
        if(!StringUtils.isEmpty(code)){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            Map<String,String> body = new HashMap<>(3);
            body.put("client_id",githubClientId);
            body.put("client_secret",githubClientSecret);
            body.put("code",code);
            HttpEntity<Map<String,String>> request = new HttpEntity<>(body,headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenLink,request,String.class);
            accessToken = response.getBody();
            JSONObject.parseArray(accessToken);
        }
        return accessToken;
    }

}
