package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserBuilder {
    private Map<String, Boolean> capabilities;
    private String description;
    private String email;
    private String firstName;
    private String lastName;
    private String name;
    private String nickname;
    private List<String> roles = new ArrayList<String>();
    private String slug;
    private String url;

    private UserBuilder() {
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder withCapabilities(Map<String, Boolean> capabilities) {
        this.capabilities = capabilities;
        return this;
    }

    public UserBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserBuilder withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public UserBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public UserBuilder but() {
        return aUser().withCapabilities(capabilities).withDescription(description).withEmail(email).withFirstName(firstName).withLastName(lastName).withName(name).withNickname(nickname).withRoles(roles).withSlug(slug).withUrl(url);
    }

    public User build() {
        User user = new User();
        user.setCapabilities(capabilities);
        user.setDescription(description);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setName(name);
        user.setNickname(nickname);
        user.setRoles(roles);
        user.setSlug(slug);
        user.setUrl(url);
        return user;
    }
}
