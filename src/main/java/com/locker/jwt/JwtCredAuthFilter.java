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
    private final JwtConfig config;
    private final JwtSecretKey secretKey;
    
    public JwtCredAuthFilter(
        AuthenticationManager auth,
        JwtConfig figged,
        JwtSecretKey sekrit
    ) 
    {
        this.manage = auth;
        this.config = figged;
        this.secretKey = sekrit;
    }

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
                    .signWith(secretKey.secretKey())
                    .compact();
        
        response.addHeader(config.getAuthHeader(), config.getTokenPrefix() + token);
    }
}
