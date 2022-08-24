package com.locker.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.security.Keys;

@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig 
{
    private String key;
    private String tokenPrefix;
    private int tokenExpirationAfterDays;
    
    public String getKey() { return key; }
    public String getTokenPrefix() { return tokenPrefix; }
    public int getTokenExpirationAfterDays() { return tokenExpirationAfterDays; }

    public void setKey(String key) { this.key = key; }
    public void setTokenPrefix(String tokenPrefix) 
    {
        this.tokenPrefix = tokenPrefix; 
    }
    public void setTokenExpirationAfterDays(int tokenExpirationAfterDays) 
    {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }

    public JwtConfig() {}

    public String getAuthHeader() { return HttpHeaders.AUTHORIZATION; }
}
