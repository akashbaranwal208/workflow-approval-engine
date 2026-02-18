package com.workflow.approval.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;

public class JwtAuthenticateFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkeymysecretkey";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-ui.html")) {

            filterChain.doFilter(request, response);
            return;
        }


        String authHeader= request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer "))
        {
            String token=authHeader.substring(7);

            Claims claims = Jwts.parser()
                    .setSigningKey(JwtConstants.SECRET_KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            String role=claims.get("role", String.class);


            UsernamePasswordAuthenticationToken authentication=
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            Collections.singletonList(()->role)
                    );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request,response);

    }
}
