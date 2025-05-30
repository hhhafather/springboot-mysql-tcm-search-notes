package com.example.exam1.service;

import com.example.exam1.dto.UserNoteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserNoteService {
    
    UserNoteDto createNote(Long userId, Long classicTextId, String content);
    
    UserNoteDto updateNote(Long userId, Long noteId, String content);
    
    void deleteNote(Long userId, Long noteId);
    
    Page<UserNoteDto> getUserNotes(Long userId, Pageable pageable);
    
    List<UserNoteDto> getUserNotesByClassicText(Long userId, Long classicTextId);
}