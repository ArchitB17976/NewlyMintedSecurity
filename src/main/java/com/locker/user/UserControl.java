package com.locker.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserControl 
{
    private final List<User> USERS = Arrays.asList(
        new User(120000, "Derek Schneider"),
        new User(120001, "Brie Ulrich"),
        new User(120002, "Kirk Lee")
    );

    @GetMapping(path = "{user_id}")
    public User getUser(@PathVariable("user_id") Long user_id)
    {
        return USERS.stream()
                        .filter(user -> user_id.equals(user.getId()))
                        .findFirst()
                        .orElseThrow(() -> 
                            new IllegalStateException(
                                "User " + user_id + " does not exist."
                            ));
    }
}
