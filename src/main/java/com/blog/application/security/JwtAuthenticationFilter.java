package com.blog.application.security;

import com.blog.application.exceptions.ApiException;
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
            if (requestToken != null && requestToken.startsWith("Bearer ")) {
                token = requestToken.substring(7);
                username = jwtTokenHelper.getUsernameFromToken(token);
            } else if (requestToken != null) {
                throw new ApiException("JWT does not start with 'Bearer '");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtTokenHelper.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new ApiException("Invalid JWT token during validation");
                }
            }
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            handleException(response, "JWT token has expired", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            handleException(response, "Invalid JWT token", HttpServletResponse.SC_BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            handleException(response, "Unable to get JWT token", HttpServletResponse.SC_BAD_REQUEST);
        } catch (ApiException e) {
            handleException(response, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            handleException(response, "An unexpected error occurred", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"error\": \"%s\", \"status\": %d}", message, status)
        );
    }
}
