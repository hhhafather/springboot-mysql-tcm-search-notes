package com.example.exam1.repository;

import com.example.exam1.entity.IncorrectAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncorrectAnswerRepository extends JpaRepository<IncorrectAnswer, Long> {
    
    Page<IncorrectAnswer> findByUserId(Long userId, Pageable pageable);
    
    Page<IncorrectAnswer> findByUserIdAndReviewed(Long userId, boolean reviewed, Pageable pageable);
    
    List<IncorrectAnswer> findByUserIdAndClassicTextId(Long userId, Long classicTextId);
    
    @Query("SELECT COUNT(i) FROM IncorrectAnswer i WHERE i.user.id = :userId AND i.reviewed = false")
    long countUnreviewedByUserId(@Param("userId") Long userId);
}