package com.example.exam1.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserNoteDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Long textId;
    private String textTitle;
}