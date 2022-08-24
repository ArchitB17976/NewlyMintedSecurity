package com.locker.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtCredAuthFilter extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager manage;
    
    public JwtCredAuthFilter(AuthenticationManager auth) { this.manage = auth; }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, 
        HttpServletResponse response
    )
    throws AuthenticationException 
    {
        try
        {
            CredAuthRequest newReq = new ObjectMapper().readValue(
                request.getInputStream(), 
                CredAuthRequest.class
            );

            Authentication authentication = 
                new UsernamePasswordAuthenticationToken(
                    newReq.getUsername(), 
                    newReq.getPassword()
                );

            return manage.authenticate(authentication);
        }
        catch(IOException e) { throw new RuntimeException(e); }
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain chain,
        Authentication authResult
    ) throws IOException, ServletException 
    {
        String key = "AsEdFrTg7YhAsEdFrTg7YdFrTg7Yh994448559494855dSDFsdffsq";
        String token = 
            Jwts.builder()
                    .setSubject(authResult.getName())
                    .claim("authorities", authResult.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(
                        java.sql.Date.valueOf(
                            LocalDate.now().plusWeeks(2)
                        )
                    )
                    .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                    .compact();
        
        response.addHeader("Authorization", "Bearer " + token);
    }
}
