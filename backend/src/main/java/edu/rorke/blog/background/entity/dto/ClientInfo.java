package edu.rorke.blog.background.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rorke
 * @date 2020/4/23 18:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfo {
    protected String ip;
    protected String browser;
    protected String cityName;
    protected String accessToken;
}
