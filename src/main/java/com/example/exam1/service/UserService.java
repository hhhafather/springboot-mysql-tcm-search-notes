package com.example.exam1.service;

import com.example.exam1.dto.UserDto;
import com.example.exam1.dto.request.RegisterRequest;
import com.example.exam1.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    
    UserDto registerUser(RegisterRequest registerRequest);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findById(Long id);
    
    UserDto updateUser(Long id, UserDto userDto);
    
    void deleteUser(Long id);
    
    Page<UserDto> getAllUsers(Pageable pageable);
    
    UserDto getUserProfile(Long id);
    
    void updateLastLogin(Long userId);
    
    void incrementLearnedTextsCount(Long userId);
}