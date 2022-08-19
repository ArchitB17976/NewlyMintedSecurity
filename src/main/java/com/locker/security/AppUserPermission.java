package com.locker.security;

public enum AppUserPermission 
{
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");
    
    private final String permission;

    public String getPermission() 
    {
        return permission;
    }

    private AppUserPermission(String permit) 
    {
        this.permission = permit;
    }

    
}
