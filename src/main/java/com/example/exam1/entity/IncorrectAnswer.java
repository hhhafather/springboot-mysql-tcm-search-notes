package com.example.exam1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "incorrect_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncorrectAnswer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "text_id", nullable = false)
    private ClassicText classicText;
    
    @Column(nullable = false)
    private String userAnswer;
    
    @Column(nullable = false)
    private String correctAnswer;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private boolean reviewed = false;
    
    private LocalDateTime reviewedAt;
}