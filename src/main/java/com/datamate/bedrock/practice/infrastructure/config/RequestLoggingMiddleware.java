package com.datamate.bedrock.practice.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingMiddleware extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
// 1. Capture the start time
        long startTime = System.currentTimeMillis();

        // 2. Log the incoming request details (Method and URI)
        String method = request.getMethod();
        String uri = request.getRequestURI();

        System.out.println(">>> Incoming Request: [" + method + "] " + uri);

        // If you don't call this, the request never reaches the Controller.
        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        System.out.println("<<< Completed Request: Status " + response.getStatus() + " (Time: " + duration + "ms)");
    }
}
