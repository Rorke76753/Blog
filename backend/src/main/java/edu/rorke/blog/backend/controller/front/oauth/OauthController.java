package edu.rorke.blog.backend.controller.front.oauth;

import edu.rorke.blog.backend.entity.oauth.AccessToken;
import edu.rorke.blog.backend.entity.oauth.OauthUser;
import edu.rorke.blog.backend.service.OauthService;
import edu.rorke.blog.backend.util.OauthUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


/**
 * @author Rorke
 * @date 2020/4/28 21:07
 */

@RestController
@RequestMapping("/api/login/oauth")
public class OauthController {

    private final OauthService oauthService;
    private final OauthUtil oauthUtil;

    public OauthController(OauthService oauthService,OauthUtil oauthUtil) {
        this.oauthService = oauthService;
        this.oauthUtil = oauthUtil;
    }

    @GetMapping("/{platform}")
    public String getGithubOauthLink(@PathVariable String platform) {
        String oauthLink = null;
        if (oauthUtil.GITHUB_PLATFORM.equals(platform)) {
            oauthLink = oauthUtil.githubOauthLink;
        }
        return oauthLink;
    }

    /**
     * 前端接收callback后通知后端向认证服务器确认code
     * @param code 前端接收callback后的code
     * @return access_token
     */
    @GetMapping("/callback/{platform}")
    public AccessToken getAccessToken(@PathVariable String platform, @RequestParam("code") String code) {
        String tokenLink = null, clientId = null, clientSecret = null;
        if (oauthUtil.GITHUB_PLATFORM.equals(platform)) {
            tokenLink = oauthUtil.githubTokenLink;
            clientId = oauthUtil.githubClientId;
            clientSecret = oauthUtil.githubClientSecret;
        }
        String accessToken = null;
        if (tokenLink != null && clientId != null && clientSecret != null){
             accessToken = oauthService.getAccessToken(tokenLink, code, clientId, clientSecret);
        }
        return AccessToken.builder().accessToken(accessToken).platform(platform).build();
    }

    /**
     * 验证+获取github用户信息
     * @param accessToken
     * @return
     */
    @PostMapping("/user")
    public OauthUser validateToken(@RequestBody AccessToken accessToken) {
        String token = accessToken.getAccessToken();
        String infoLink = null;
        if (oauthUtil.GITHUB_PLATFORM.equals(accessToken.getPlatform())) {
            infoLink = oauthUtil.githubInfoLink;
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
