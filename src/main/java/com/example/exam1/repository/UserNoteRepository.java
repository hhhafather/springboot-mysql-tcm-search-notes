package com.example.exam1.repository;

import com.example.exam1.entity.UserNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNoteRepository extends JpaRepository<UserNote, Long> {
    
    Page<UserNote> findByUserId(Long userId, Pageable pageable);
    
    List<UserNote> findByUserIdAndClassicTextId(Long userId, Long classicTextId);
    
    Optional<UserNote> findByUserIdAndClassicTextIdAndId(Long userId, Long classicTextId, Long noteId);
}