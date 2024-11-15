package com.example.jwtproject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;

public class IPUtils {
    private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // 여러 IP가 있는 경우 첫 번째 IP만 사용
            ip = ip.split(",")[0];
        } else {
            ip = request.getHeader("Proxy-Client-IP");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }

        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        logger.info("Extracted Client IP Address: " + ip);
        return ip;
    }
}

