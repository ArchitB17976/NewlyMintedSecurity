package com.locker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.locker.security.AppUserRole.*;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
            .csrf().disable()
            .authorizeRequests() // Provide ability to authorize requests
            
            // Whitelisting listed pages for everyone
            .antMatchers("/", "index","/css/*", "/js/*").permitAll() 
            
            // Whitelisting URL endpoints under "api" for normal users
            .antMatchers("/api/**").hasRole(NORMAL_USER.name())
            
            .anyRequest() // Counts for any requests
            .authenticated() // Must be authenticated
            .and()
            .formLogin()
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/courses", true)
            .and()
            .rememberMe()
                // Extends remember me time limit from default (2 weeks)
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                // Setting up the key to scramble the information in the cookie
                .key("somethingveryverysecure");
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
                                        // ROLE_NORMAL_USER
                                        .authorities(NORMAL_USER.getGrantedAuths())
                                        .build();
        
        UserDetails kirk = User.builder()
                                        .username("kirk")
                                        .password(encode.encode("pass123"))
                                        .roles(ADMIN.name()) // ROLE_ADMIN
                                        .authorities(ADMIN.getGrantedAuths())
                                        .build();
       
        UserDetails derek = User.builder()
                                        .username("derek")
                                        .password(encode.encode("pass456"))
                                        .roles(ADMIN_TRAINEE.name())
                                        // ROLE_ADMIN_TRAINEE
                                        .authorities(ADMIN_TRAINEE.getGrantedAuths())
                                        .build();

        return new InMemoryUserDetailsManager(anna, kirk, derek);
    }
}
