package edu.rorke.blog.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Rorke
 * @date 2020/5/5 13:19
 */
@Component
@ConfigurationProperties(prefix = "oauth2")
public class OauthUtil {
    public final String GITHUB_PLATFORM = "Github";
    public final String GITEE_PLATFORM = "Gitee";
    @Value("${oauth2.github.client-id}")
    public String githubClientId;

    @Value("${oauth2.github.client-secret}")
    public String githubClientSecret;

    @Value("${oauth2.github.oauthLink}")
    public String githubOauthLink;

    @Value("${oauth2.github.tokenLink}")
    public String githubTokenLink;

    @Value("${oauth2.github.infoLink}")
    public String githubInfoLink;
}
