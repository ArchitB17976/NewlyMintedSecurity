package com.locker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.locker.security.AppUserRole.*;

@Configuration
@EnableWebSecurity

public class AppSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final PasswordEncoder encode;

    @Autowired
    public AppSecurityConfig(PasswordEncoder encodes)
    {
        this.encode = encodes;
    }
    
    @Override
    protected void configure(HttpSecurity http) 
        throws Exception 
    {
        http
            .authorizeRequests() // Provide ability to authorize requests
            
            // Whitelisting listed pages for everyone
            .antMatchers("/", "index","/css/*", "/js/*").permitAll() 
            
            // Whitelisting URL endpoints under "api" for normal users
            .antMatchers("/api/**").hasRole(NORMAL_USER.name())
            
            .anyRequest() // Counts for any requests
            .authenticated() // Must be authenticated
            .and()
            .httpBasic(); // Using basic HTTP for enforcement
    }
    
    // Creating one user instance
    @Override
    @Bean
    protected UserDetailsService userDetailsService() 
    {
        UserDetails anna = User.builder()
                                        .username("brie")
                                        .password(encode.encode("pass"))
                                        .roles(NORMAL_USER.name())
                                        .build();
        
        UserDetails kirk = User.builder()
                                        .username("kirk")
                                        .password(encode.encode("pass123"))
                                        .roles(ADMIN.name())
                                        .build();

        return new InMemoryUserDetailsManager(anna, kirk);
    }
}
