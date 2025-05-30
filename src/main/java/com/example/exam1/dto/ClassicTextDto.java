package com.example.exam1.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassicTextDto {
    private Long id;
    private String title;
    private String content;
    private String source;
    private String category;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}