package com.example.exam1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classic_texts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassicText {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String content;
    
    private String source;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "classicText", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNote> notes = new HashSet<>();
    
    @OneToMany(mappedBy = "classicText", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IncorrectAnswer> incorrectAnswers = new HashSet<>();
    
    // 学习统计
    private int viewCount = 0;
}