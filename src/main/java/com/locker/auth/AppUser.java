package com.locker.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUser implements UserDetails
{
    private final List<? extends GrantedAuthority> grantedAuths;
    private final String username, password;
    private final boolean 
        isAccountNonExpired, isAccountNonLocked, 
        isCredentialsNonExpired, isEnabled;
    
    public AppUser(
        List<? extends GrantedAuthority> grantedAuths, 
        String username, 
        String password,
        boolean isAccountNonExpired, 
        boolean isAccountNonLocked, 
        boolean isCredentialsNonExpired,
        boolean isEnabled
    ) 
    {
        this.grantedAuths = grantedAuths;
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return grantedAuths;
    }

    @Override
    public String getPassword() 
    { 
        return password;
    }

    @Override
    public String getUsername() 
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() 
    {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() 
    {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() 
    {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() 
    {
        return isEnabled;
    }
}