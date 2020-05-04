package edu.rorke.blog.background.service.impl.oauth;

import com.alibaba.fastjson.JSONObject;
import edu.rorke.blog.background.entity.oauth.OauthUser;
import edu.rorke.blog.background.service.OauthService;
import edu.rorke.blog.background.util.JwtUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Fix connection reset
 * @author Rorke
 * @date 2020/4/30 17:46
 */
@Service
public class GithubOauthServiceImpl implements OauthService {

    private final RestTemplate restTemplate;

    public GithubOauthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getAccessToken(String url, String code, String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON_VALUE);
        Map<String,String> map = new HashMap<>(3);
        map.put("client_id",clientId);
        map.put("client_secret",clientSecret);
        map.put("code",code);
        HttpEntity<Map<String,String>> request = new HttpEntity<>(map,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url,request,String.class);
        Map<String,String> jsonMap = (Map<String, String>) JSONObject.parse(response.getBody());
        return jsonMap == null ? null : jsonMap.get("access_token");
    }

    @Override
    public OauthUser getUserInfoFromToken(String url, String accessToken) throws HttpClientErrorException.Unauthorized {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "token " + accessToken);
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        Map<String, String> jsonMap = null;
        try{
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            jsonMap = (Map<String, String>) JSONObject.parse(response.getBody());
        }catch (RestClientException e){
            return OauthUser.builder().errorMessage("Currently unable to connect with Github user info server,try again later").build();
        }
        return jsonMap == null ? null : !isGoodCredential(jsonMap) ? null : OauthUser.builder()
                                                                                            .accessToken(accessToken)
                                                                                            .username(jsonMap.get("login"))
                                                                                            .htmlUrl(jsonMap.get("html_url"))
                                                                                            .avatarUrl(jsonMap.get("avatar_url"))
                                                                                            .build();
    }

    private boolean isGoodCredential(Map<String, String> jsonMap) {
        return jsonMap.containsKey("login")&&
                jsonMap.containsKey("html_url")&&
                jsonMap.containsKey("avatar_url");
    }
}
