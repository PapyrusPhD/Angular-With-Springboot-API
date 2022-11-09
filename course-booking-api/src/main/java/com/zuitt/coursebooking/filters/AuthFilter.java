package com.zuitt.coursebooking.filters;

import com.zuitt.coursebooking.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(
        ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;

        // Skip authentication if the following conditions below are met.
        if (isRequestWhitelisted(httpRequest.getMethod(), httpRequest.getRequestURI())) {
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");
        String[] authHeaderArr = (authHeader == null) ? new String[] {} : authHeader.split("Bearer ");

        if (authHeader == null) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization header must be provided.");
            return;
        }

        if (authHeaderArr.length <= 1) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be 'Bearer [token]'.");
            return;
        }

        try {
            String token = authHeaderArr[1];
            Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();
            httpRequest.setAttribute("id", claims.get("id"));
            httpRequest.setAttribute("email", claims.get("email").toString());
        } catch (Exception exception) {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid or expired token.");
            return;
        }

        filterChain.doFilter(httpRequest, httpResponse);
    }

    public boolean isRequestWhitelisted(String method, String path) {
        if (method.equals("GET")) {
            if (path.contains("/api/courses")) {
                return true;
            }
        }

        return false;
    }
}
