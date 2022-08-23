package com.locker.auth;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import static com.locker.security.AppUserRole.*;

@Repository("Fake") // Name used in case of multiple repo implementations
public class FakeAppUseDaoServe implements AppUserDAO
{
    private final PasswordEncoder encoding;

    @Autowired
    public FakeAppUseDaoServe(PasswordEncoder encode){ this.encoding = encode; }

    @Override
    public Optional<AppUser> selectAppUserByUsername(String username) 
    {
        return getAppUsers()
                .stream()
                .filter(appUser -> username.equals(appUser.getUsername()))
                .findFirst();
    }

    private List<AppUser> getAppUsers()
    {
        return Lists.newArrayList(
            new AppUser(
                NORMAL_USER.getGrantedAuths(),
                "brie",
                encoding.encode("pass"),
                true,
                true,
                true,
                true
            ),
            new AppUser(
                ADMIN.getGrantedAuths(),
                "kirk",
                encoding.encode("pass123"),
                true,
                true,
                true,
                true
            ),
            new AppUser(
                ADMIN_TRAINEE.getGrantedAuths(),
                "derek",
                encoding.encode("pass456"),
                true,
                true,
                true,
                true
            )
        );
    }
}
