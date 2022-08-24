package com.locker.jwt;

import java.io.IOException;

import javax.servlet.http.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
}
