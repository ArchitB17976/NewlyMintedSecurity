package com.locker.security;

public enum AppUserPermission 
{
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
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
