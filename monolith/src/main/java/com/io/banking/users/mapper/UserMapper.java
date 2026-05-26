package com.io.banking.users.mapper;

import com.io.banking.users.model.dto.UserResponse;
import com.io.banking.users.model.entity.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setRole(user.getRole().name());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
