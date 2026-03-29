package com.example.oauth2.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService blacklist;
    private final UserDetailsService userDetails;

    public JwtAuthFilter (JwtUtil jwtUtil, TokenBlacklistService blacklist, UserDetailsService userDetails){
        this.jwtUtil=jwtUtil;
        this.blacklist=blacklist;
        this.userDetails=userDetails;
    }

    @Override
    protected void doFilterInternal (HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = req.getHeader("Authorization");
        
       if(authHeader != null && authHeader.startsWith("Bearer ")){
        // Extract the token 
        String token = authHeader.substring(7);

        if(!blacklist.isBlacklisted(token)){
            String username = jwtUtil.extractUsername(token);
            UserDetails user = this.userDetails.loadUserByUsername(username);

            if(jwtUtil.validateToken(user, token) && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
                );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(req, res);



       }





    }
}