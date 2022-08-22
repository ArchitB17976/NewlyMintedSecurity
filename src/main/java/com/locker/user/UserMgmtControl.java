package com.locker.user;

import java.util.*;

import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public void registerNewUser(@RequestBody UserEntity user)
    {
        System.out.println(user);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<UserEntity> getUSERS() 
    {
        return USERS;
    }

    @PutMapping(path = "{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void updateUser
        (@PathVariable long userId, @RequestBody UserEntity user)
    {
        System.out.println(String.format("%s %s", userId, user));
    }

    @DeleteMapping(path = "{userId}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteUser(@PathVariable(name = "userId") long userId)
    {
        System.out.println(userId);
    }
}
