package edu.rorke.blog.background.controller.front.oauth;

import edu.rorke.blog.background.entity.oauth.AccessToken;
import edu.rorke.blog.background.entity.oauth.OauthUser;
import edu.rorke.blog.background.service.OauthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


/**
 * @author Rorke
 * @date 2020/4/28 21:07
 */

@RestController
@RequestMapping("/api/login/oauth")
public class OauthController {
    @Value("${oauth2.github.client-id}")
    private String githubClientId;

    @Value("${oauth2.github.client-secret}")
    private String githubClientSecret;

    @Value("${oauth2.github.oauthLink}")
    private String githubOauthLink;

    @Value("${oauth2.github.tokenLink}")
    private String githubTokenLink;

    @Value("${oauth2.github.infoLink}")
    private String githubInfoLink;

    private final OauthService oauthService;
    private final String GITHUB_PLATFORM = "Github";
    private final String GITEE_PLATFORM = "Gitee";


    public OauthController(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping
    public String getGithubOauthLink() {
        return githubOauthLink;
    }

    /**
     * 前端接收callback后通知后端向认证服务器确认code
     *
     * @param code 前端接收callback后的code
     * @return access_token
     */
    @GetMapping("/callback")
    public AccessToken githubCallBack(@RequestParam("code") String code) {
        String accessToken = oauthService.getAccessToken(githubTokenLink, code, githubClientId, githubClientSecret);
        return AccessToken.builder().accessToken(accessToken).platform("Github").build();
    }

    /**
     * 验证+获取github用户信息
     *
     * @param accessToken
     * @return
     */
    @PostMapping("/user")
    public OauthUser validateToken(@RequestBody AccessToken accessToken) {
        String token = accessToken.getAccessToken();
        String infoLink = null;
        if (GITHUB_PLATFORM.equals(accessToken.getPlatform())) {
            infoLink = githubInfoLink;
        } else if (GITEE_PLATFORM.equals(accessToken.getPlatform())) {
            //infoLink = giteeInfoLink;
        }
        OauthUser oauthUser = OauthUser.builder().build();
        if (infoLink != null) {
            try {
                oauthUser = oauthService.getUserInfoFromToken(infoLink, token);
            } catch (HttpClientErrorException.Unauthorized unauthorized) {
                oauthUser.setErrorMessage("该token并不可靠，请重新登录");
            }
        }
        return oauthUser;
    }
}
