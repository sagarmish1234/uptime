package com.uptime.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ReactAppInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (!path.startsWith("/api/v1") && !path.contains(".")) {
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        }
        return true;
    }
}