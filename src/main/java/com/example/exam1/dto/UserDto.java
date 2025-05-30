package com.example.exam1.dto;

import com.example.exam1.entity.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private LocalDateTime lastLoginTime;
    private int learnedTextsCount;
    private LocalDateTime createdAt;
}