package edu.rorke.blog.background.entity.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/5/3 0:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessToken {
    private String accessToken;
    private String platform;
}
