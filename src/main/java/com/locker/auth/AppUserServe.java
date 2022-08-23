package com.locker.auth;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AppUserServe implements UserDetailsService
{
    @Override
    public UserDetails loadUserByUsername(String username) 
        throws UsernameNotFoundException 
    {
        return null;
    }
}
