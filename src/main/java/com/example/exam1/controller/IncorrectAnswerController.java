package com.example.exam1.controller;

import com.example.exam1.dto.IncorrectAnswerDto;
import com.example.exam1.dto.response.PageResponse;
import com.example.exam1.entity.User;
import com.example.exam1.repository.UserRepository;
import com.example.exam1.service.IncorrectAnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/incorrect-answers")
public class IncorrectAnswerController {
    
    @Autowired
    private IncorrectAnswerService incorrectAnswerService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageResponse<IncorrectAnswerDto>> getUserIncorrectAnswers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        Page<IncorrectAnswerDto> answerPage = incorrectAnswerService.getUserIncorrectAnswers(userId, PageRequest.of(page, size));
        
        PageResponse<IncorrectAnswerDto> response = new PageResponse<>(
                answerPage.getContent(),
                answerPage.getTotalElements(),
                answerPage.getTotalPages(),
                answerPage.getSize(),
                answerPage.getNumber(),
                answerPage.isFirst(),
                answerPage.isLast(),
                answerPage.isEmpty()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/unreviewed")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageResponse<IncorrectAnswerDto>> getUserUnreviewedAnswers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        Page<IncorrectAnswerDto> answerPage = incorrectAnswerService.getUserUnreviewedAnswers(userId, PageRequest.of(page, size));
        
        PageResponse<IncorrectAnswerDto> response = new PageResponse<>(
                answerPage.getContent(),
                answerPage.getTotalElements(),
                answerPage.getTotalPages(),
                answerPage.getSize(),
                answerPage.getNumber(),
                answerPage.isFirst(),
                answerPage.isLast(),
                answerPage.isEmpty()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/text/{textId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<IncorrectAnswerDto>> getUserIncorrectAnswersByClassicText(
            @PathVariable Long textId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        List<IncorrectAnswerDto> incorrectAnswers = incorrectAnswerService.getUserIncorrectAnswersByClassicText(userId, textId);
        return ResponseEntity.ok(incorrectAnswers);
    }
    
    @PostMapping("/text/{textId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IncorrectAnswerDto> recordIncorrectAnswer(
            @PathVariable Long textId,
            @Valid @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        String userAnswer = payload.get("userAnswer");
        String correctAnswer = payload.get("correctAnswer");
        
        if (userAnswer == null || correctAnswer == null) {
            return ResponseEntity.badRequest().build();
        }
        
        IncorrectAnswerDto incorrectAnswer = incorrectAnswerService.recordIncorrectAnswer(
                userId, textId, userAnswer, correctAnswer);
        return ResponseEntity.ok(incorrectAnswer);
    }
    
    @PutMapping("/{answerId}/review")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> markAsReviewed(
            @PathVariable Long answerId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        incorrectAnswerService.markAsReviewed(userId, answerId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/unreviewed/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Long>> getUnreviewedCount(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        long count = incorrectAnswerService.countUserUnreviewedAnswers(userId);
        return ResponseEntity.ok(Map.of("count", count));
    }
    
    private Long extractUserId(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}