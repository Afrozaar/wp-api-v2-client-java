package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.model.User;

import java.util.List;

public interface Users {
    List<User> getUsers();

    User createUser(User user, String username, String password);

    User getUser(long userId);
    User getUser(long userId, String context);

    User updateUser(User user);
    User deleteUser(User user);
}
