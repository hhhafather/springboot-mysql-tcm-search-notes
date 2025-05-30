package com.example.exam1.repository;

import com.example.exam1.entity.ClassicText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassicTextRepository extends JpaRepository<ClassicText, Long> {
    
    Page<ClassicText> findByCategory(String category, Pageable pageable);
    
    @Query("SELECT c FROM ClassicText c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ClassicText> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}