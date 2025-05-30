package com.example.exam1.service.impl;

import com.example.exam1.dto.IncorrectAnswerDto;
import com.example.exam1.entity.ClassicText;
import com.example.exam1.entity.IncorrectAnswer;
import com.example.exam1.entity.User;
import com.example.exam1.repository.ClassicTextRepository;
import com.example.exam1.repository.IncorrectAnswerRepository;
import com.example.exam1.repository.UserRepository;
import com.example.exam1.service.IncorrectAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncorrectAnswerServiceImpl implements IncorrectAnswerService {
    
    private final IncorrectAnswerRepository incorrectAnswerRepository;
    private final UserRepository userRepository;
    private final ClassicTextRepository classicTextRepository;
    
    @Autowired
    public IncorrectAnswerServiceImpl(
            IncorrectAnswerRepository incorrectAnswerRepository,
            UserRepository userRepository,
            ClassicTextRepository classicTextRepository) {
        this.incorrectAnswerRepository = incorrectAnswerRepository;
        this.userRepository = userRepository;
        this.classicTextRepository = classicTextRepository;
    }
    
    @Override
    @Transactional
    public IncorrectAnswerDto recordIncorrectAnswer(Long userId, Long classicTextId, String userAnswer, String correctAnswer) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        ClassicText classicText = classicTextRepository.findById(classicTextId)
                .orElseThrow(() -> new RuntimeException("条文不存在"));
        
        IncorrectAnswer incorrectAnswer = new IncorrectAnswer();
        incorrectAnswer.setUser(user);
        incorrectAnswer.setClassicText(classicText);
        incorrectAnswer.setUserAnswer(userAnswer);
        incorrectAnswer.setCorrectAnswer(correctAnswer);
        incorrectAnswer.setCreatedAt(LocalDateTime.now());
        incorrectAnswer.setReviewed(false);
        
        IncorrectAnswer savedAnswer = incorrectAnswerRepository.save(incorrectAnswer);
        return mapToDto(savedAnswer);
    }
    
    @Override
    @Transactional
    public void markAsReviewed(Long userId, Long incorrectAnswerId) {
        IncorrectAnswer incorrectAnswer = incorrectAnswerRepository.findById(incorrectAnswerId)
                .orElseThrow(() -> new RuntimeException("错题记录不存在"));
        
        if (!incorrectAnswer.getUser().getId().equals(userId)) {
            throw new RuntimeException("无权操作此错题记录");
        }
        
        incorrectAnswer.setReviewed(true);
        incorrectAnswer.setReviewedAt(LocalDateTime.now());
        incorrectAnswerRepository.save(incorrectAnswer);
    }
    
    @Override
    public Page<IncorrectAnswerDto> getUserIncorrectAnswers(Long userId, Pageable pageable) {
        return incorrectAnswerRepository.findByUserId(userId, pageable).map(this::mapToDto);
    }
    
    @Override
    public Page<IncorrectAnswerDto> getUserUnreviewedAnswers(Long userId, Pageable pageable) {
        return incorrectAnswerRepository.findByUserIdAndReviewed(userId, false, pageable).map(this::mapToDto);
    }
    
    @Override
    public List<IncorrectAnswerDto> getUserIncorrectAnswersByClassicText(Long userId, Long classicTextId) {
        return incorrectAnswerRepository.findByUserIdAndClassicTextId(userId, classicTextId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public long countUserUnreviewedAnswers(Long userId) {
        return incorrectAnswerRepository.countUnreviewedByUserId(userId);
    }
    
    private IncorrectAnswerDto mapToDto(IncorrectAnswer incorrectAnswer) {
        IncorrectAnswerDto dto = new IncorrectAnswerDto();
        dto.setId(incorrectAnswer.getId());
        dto.setUserAnswer(incorrectAnswer.getUserAnswer());
        dto.setCorrectAnswer(incorrectAnswer.getCorrectAnswer());
        dto.setReviewed(incorrectAnswer.isReviewed());
        dto.setCreatedAt(incorrectAnswer.getCreatedAt());
        dto.setReviewedAt(incorrectAnswer.getReviewedAt());
        dto.setUserId(incorrectAnswer.getUser().getId());
        dto.setTextId(incorrectAnswer.getClassicText().getId());
        dto.setTextTitle(incorrectAnswer.getClassicText().getTitle());
        return dto;
    }
}