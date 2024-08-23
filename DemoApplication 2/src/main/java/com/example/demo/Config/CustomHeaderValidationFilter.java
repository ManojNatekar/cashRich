package com.example.demo.Config;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomHeaderValidationFilter extends OncePerRequestFilter {
	
	private static final String EXPECTED_HEADER_KEY = "X-Custom-Header";
    private static final String EXPECTED_HEADER_VALUE = "CustomValue";

    
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String headerValue = request.getHeader(EXPECTED_HEADER_KEY);

        if (EXPECTED_HEADER_VALUE.equals(headerValue)) {
            chain.doFilter(request, response); // Continue with the filter chain
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Invalid header value");
        }
    }

}
