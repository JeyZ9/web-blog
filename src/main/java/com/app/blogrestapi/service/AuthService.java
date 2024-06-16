package com.app.blogrestapi.service;

import com.app.blogrestapi.dto.LoginDTO;
import com.app.blogrestapi.dto.RegisterDTO;
import com.app.blogrestapi.model.User;

import java.util.List;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);

    List<User> getUserTest();
}
