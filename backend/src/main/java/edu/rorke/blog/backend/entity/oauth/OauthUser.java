package edu.rorke.blog.backend.entity.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/4/29 17:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OauthUser {
    protected String accessToken;
    protected String username;
    protected String htmlUrl;
    protected String avatarUrl;
    protected String errorMessage;
}
