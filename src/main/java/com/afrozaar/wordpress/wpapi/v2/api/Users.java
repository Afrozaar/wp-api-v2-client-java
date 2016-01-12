package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.UserNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.User;

import java.util.List;

public interface Users {
    List<User> getUsers();

    User createUser(User user, String username, String password);

    User getUser(long userId) throws UserNotFoundException;
    User getUser(long userId, String context) throws UserNotFoundException;

    User updateUser(User user);
    User deleteUser(User user);
}
