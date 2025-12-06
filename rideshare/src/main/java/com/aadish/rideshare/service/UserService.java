package com.aadish.rideshare.service;

import com.aadish.rideshare.model.User;

public interface UserService {
    User register(User user);
    User login(String email, String password);
}
