package com.locker.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
import static com.locker.security.AppUserPermission.*;

public enum AppUserRole 
{
    NORMAL_USER(Sets.newHashSet()),
    ADMIN_TRAINEE(Sets.newHashSet(
        COURSE_READ, USER_READ
    )),
    ADMIN(Sets.newHashSet(
        COURSE_READ, COURSE_WRITE,
        USER_READ, USER_WRITE
    ));
    
    private final Set<AppUserPermission> permissions;
    
    public Set<AppUserPermission> getPermissions() { return permissions; }
    AppUserRole(Set<AppUserPermission> permits) { this.permissions = permits; }
    public Set<SimpleGrantedAuthority> getGrantedAuths()
    {
        Set<SimpleGrantedAuthority> listedAuths = 
            getPermissions()
                    .stream()
                    .map(
                        permission -> 
                        new SimpleGrantedAuthority(permission.getPermission())
                    )
                    .collect(Collectors.toSet());
        listedAuths.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return listedAuths;
    }
}
