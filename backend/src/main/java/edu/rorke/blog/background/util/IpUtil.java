package edu.rorke.blog.background.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Rorke
 * @date 2020/4/18 10:12
 */

public class IpUtil {
    private static final String UNKNOWN_NETWORK = "unknown";
    private static final String LOCAL_HOST = "127.0.0.1";
    private static final String SEPARATOR = ",";
    private static final Integer IP_LENGTH = 15;

    public static String getIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
