package com.io.banking.users.service;

import com.io.banking.shared.exception.ResourceNotFoundException;
import com.io.banking.users.model.dto.UserResponse;
import com.io.banking.users.model.entity.User;
import com.io.banking.users.model.enums.Role;
import com.io.banking.users.persistence.UserRepository;
import com.io.banking.users.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user1@test.com");
        user1.setRole(Role.USER);

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("user2@test.com");
        user2.setRole(Role.ADMIN);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponse> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1@test.com", result.get(0).getEmail());
        assertEquals("USER", result.get(0).getRole());
        assertEquals("user2@test.com", result.get(1).getEmail());
        assertEquals("ADMIN", result.get(1).getRole());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user1@test.com");
        user.setRole(Role.USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("user1@test.com", foundUser.getEmail());
        assertEquals("USER", foundUser.getRole());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(99L));

        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void deleteUserById_success() {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteUserById(id);

        verify(userRepository).findById(id);
        verify(userRepository).delete(user);
    }

    @Test
    void deleteUserById_userNotFound() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> userService.deleteUserById(id));

        assertEquals("User with id " + id + " not found", exception.getMessage());

        verify(userRepository).findById(id);
        verify(userRepository, never()).delete(any());
    }
}
