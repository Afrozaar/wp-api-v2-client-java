package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.UserNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.User;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.function.Function;

public interface Users {
    List<User> getUsers();
    List<User> getUsers(String context);

    User createUser(User user, String username, String password);

    User getUser(long userId) throws UserNotFoundException;
    User getUser(long userId, String context) throws UserNotFoundException;

    User updateUser(User user);
    User deleteUser(User user);

    Function<User, MultiValueMap> userMap = input -> {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        //capabilities
        map.add("description", input.getDescription());
        map.add("email", input.getEmail()); //Required: true
        map.add("first_name", input.getFirstName());
        map.add("last_name", input.getLastName());
        map.add("name", input.getName());
        map.add("nickname", input.getNickname());
        input.getRoles().forEach(role -> map.add("role", role));
        map.add("slug", input.getSlug());

        return map;
    };
}
