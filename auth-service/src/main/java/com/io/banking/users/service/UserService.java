package com.io.banking.users.service;

import com.io.banking.users.model.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUserById(Long id);
}
