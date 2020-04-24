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
    public String ip;
    public String browser;
    public String cityName;
}
