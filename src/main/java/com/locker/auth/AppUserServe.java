package com.locker.auth;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AppUserServe implements UserDetailsService
{
    private final AppUserDAO dataObj;

    @Autowired
    public AppUserServe(@Qualifier("Fake") AppUserDAO appDAO)
    {
        this.dataObj = appDAO;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) 
        throws UsernameNotFoundException 
    {
        return dataObj
                    .selectAppUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Username %s not found.", username)
                    ));
    }
}
