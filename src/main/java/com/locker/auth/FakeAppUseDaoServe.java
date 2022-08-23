package com.locker.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.locker.security.AppUserRole.*;

public class FakeAppUseDaoServe implements AppUserDAO
{
    private final PasswordEncoder encoding;

    @Autowired
    public FakeAppUseDaoServe(PasswordEncoder encoder)
    {
        this.encoding = encoder;
    }

    @Override
    public Optional<AppUser> selectAppUserByUsername(String username) 
    {
        return Optional.empty();
    }

    private List<AppUser> getAppUsers()
    {
        List<AppUser> appUsers = List.new ArrayList(
            new AppUser(
                ADMIN.getGrantedAuths(), 
                "kirk",
                encoding.encode("pass123")
            )
        )
    }
}
