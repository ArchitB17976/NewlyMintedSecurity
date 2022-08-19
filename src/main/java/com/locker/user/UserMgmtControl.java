package com.locker.user;

import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management/api/v1/users")
public class UserMgmtControl 
{
    private final List<UserEntity> USERS = Arrays.asList(
        new UserEntity(120000, "Derek Schneider"),
        new UserEntity(120001, "Brie Ulrich"),
        new UserEntity(120002, "Kirk Lee")
    );

    @PostMapping(path = "new")
    public void registerNewUser(@RequestBody UserEntity user)
    {
        System.out.println(user);
    }

    @GetMapping(path = "")
    public List<UserEntity> getUSERS() 
    {
        return USERS;
    }

    @PutMapping(path = "{userId}")
    public void updateUser(long userId, UserEntity user)
    {
        System.out.println(String.format("%s %s", userId, user));
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable(name = "userId") long userId)
    {
        System.out.println(userId);
    }
}
