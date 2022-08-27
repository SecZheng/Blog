package com.sec.singletable.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class IPUtils {
    private IPUtils(){}

    public static String getIP() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-real-ip");
        if (ip != null) {
            return ip;
        }
        ip = request.getHeader("x-forwarded-for");
        if (ip != null) {
            int index = ip.indexOf(',');
            return index == -1 ? ip : ip.substring(0, index);
        }
        return request.getRemoteAddr();
    }
}
