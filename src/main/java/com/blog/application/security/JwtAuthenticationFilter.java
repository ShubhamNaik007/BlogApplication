package com.blog.application.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");

        String username = null;
        String token = null;

        try {
            // Check if the token is present and starts with "Bearer"
            if (requestToken != null && requestToken.startsWith("Bearer ")) {
                token = requestToken.substring(7);
                username = jwtTokenHelper.getUsernameFromToken(token);
            } else if (requestToken != null) {
                throw new MalformedJwtException("JWT does not start with 'Bearer '");
            }
        } catch (IllegalArgumentException e) {
            throw new ServletException("Unable to get JWT token", e);
        } catch (ExpiredJwtException e) {
            throw new ServletException("JWT token has expired", e);
        } catch (MalformedJwtException e) {
            throw new ServletException("Invalid JWT token", e);
        }

        // Once we have the token, validate it
        try {
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtTokenHelper.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new ServletException("Invalid JWT token during validation");
                }
            }
        } catch (Exception e) {
            throw new ServletException("Authentication failed for token: " + token, e);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
