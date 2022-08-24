package com.locker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.locker.auth.AppUserServe;

import static com.locker.security.AppUserRole.*;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final PasswordEncoder encode;
    private final AppUserServe serve;

    @Autowired
    public AppSecurityConfig(PasswordEncoder encodes, AppUserServe serves)
    {
        this.encode = encodes;
        this.serve = serves;
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
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/courses", true)
                /*  parameter argument must match the "name" of
                 the input type in the html file */
                .usernameParameter("user")
                .passwordParameter("pass")
            .and()
            .rememberMe()
                // Extends remember me time limit from default (2 weeks)
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                // Setting up the key to scramble the information in the cookie
                .key("somethingveryverysecure")
                .rememberMeParameter("rem-me")
            .and()
            .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/login");
    }
    
    // Provides user instances
    @Bean
    public DaoAuthenticationProvider provideDAOAuth()
    {
        DaoAuthenticationProvider provides = new DaoAuthenticationProvider();
        provides.setPasswordEncoder(encode);
        provides.setUserDetailsService(serve);
        return provides;
    }

    // Wires things up
    @Override
    public void configure(AuthenticationManagerBuilder auth)
        throws Exception
    {
        auth.authenticationProvider(provideDAOAuth());
    }
}
