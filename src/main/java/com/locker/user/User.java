package com.locker.user;

public class User 
{
    private final long id;
    private final String name;
    
    public long getId() 
    {
        return id;
    }
    
    public String getName() 
    {
        return name;
    }

    public User(long id, String name) 
    {
        this.id = id;
        this.name = name;
    }


}
