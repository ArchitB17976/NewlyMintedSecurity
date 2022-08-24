package com.locker.jwt;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.*;

public class TokenVerifier extends OncePerRequestFilter
{
    private final JwtSecretKey secret;
    private final JwtConfig config;
    
    
    public TokenVerifier(JwtSecretKey secret, JwtConfig config) 
    {
        this.secret = secret;
        this.config = config;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) 
    throws ServletException, IOException 
    {
        String authHeader = request.getHeader(config.getAuthHeader());
        
        if(Strings.isNullOrEmpty(authHeader) || 
            !authHeader.startsWith(config.getTokenPrefix()))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.replace(config.getTokenPrefix(), "");

        try
        {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secret.secretKey())
                    .parseClaimsJws(token);
            
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            var auths = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simple = 
                auths.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication 
                = new UsernamePasswordAuthenticationToken(
                username,
                null,
                simple
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch(JwtException j) 
        {
             throw new IllegalStateException(
                String.format("Token %s cannot be trusted.", token)
             ); 
        }

        /* Ensures tha "request" and "response" are passed over to the next 
        filter*/
        filterChain.doFilter(request, response);
    }
}
