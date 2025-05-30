package com.example.exam1.service;

import com.example.exam1.dto.IncorrectAnswerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IncorrectAnswerService {
    
    IncorrectAnswerDto recordIncorrectAnswer(Long userId, Long classicTextId, String userAnswer, String correctAnswer);
    
    void markAsReviewed(Long userId, Long incorrectAnswerId);
    
    Page<IncorrectAnswerDto> getUserIncorrectAnswers(Long userId, Pageable pageable);
    
    Page<IncorrectAnswerDto> getUserUnreviewedAnswers(Long userId, Pageable pageable);
    
    List<IncorrectAnswerDto> getUserIncorrectAnswersByClassicText(Long userId, Long classicTextId);
    
    long countUserUnreviewedAnswers(Long userId);
}