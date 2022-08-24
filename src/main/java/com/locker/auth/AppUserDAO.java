package com.locker.auth;

import java.util.Optional;

public interface AppUserDAO 
{
    public Optional<AppUser> selectAppUserByUsername(String username);
}
