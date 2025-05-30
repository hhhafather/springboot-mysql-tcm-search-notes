package com.example.exam1.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IncorrectAnswerDto {
    private Long id;
    private String userAnswer;
    private String correctAnswer;
    private boolean reviewed;
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;
    private Long userId;
    private Long textId;
    private String textTitle;
}