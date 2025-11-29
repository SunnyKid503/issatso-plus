package com.university.forum.forummanagement.filters;

import com.university.forum.forummanagement.shared.utils.RequestHeaderInjector;
import com.university.forum.usermanagement.Utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Deprecated
//@Configuration
public class AuthHeaderFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");
        UUID memberId = jwtTokenUtil.getUserIdFromToken(token);

        RequestHeaderInjector customWrappedRequest = new RequestHeaderInjector(request);
        customWrappedRequest.addHeader("x-user-id", memberId.toString());

        filterChain.doFilter(customWrappedRequest, response);
    }
}
