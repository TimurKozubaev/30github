package com.kozubaev.ayu.osago.project.endpoint;

import com.kozubaev.ayu.osago.project.service.UserService;

public class Endpoint {
    private final UserService userService;

    public Endpoint(UserService userService) {
        this.userService = userService;
    }
}
