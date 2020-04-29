package edu.rorke.blog.background.entity.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/4/29 17:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicOauthUser {
    protected String username;
    protected String userPlatform;
    protected String userAvatar;
}
