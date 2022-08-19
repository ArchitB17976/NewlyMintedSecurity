package com.locker.security;

import java.util.Set;

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
}
