package com.example.exam1.service.impl;

import com.example.exam1.dto.UserNoteDto;
import com.example.exam1.entity.ClassicText;
import com.example.exam1.entity.User;
import com.example.exam1.entity.UserNote;
import com.example.exam1.repository.ClassicTextRepository;
import com.example.exam1.repository.UserNoteRepository;
import com.example.exam1.repository.UserRepository;
import com.example.exam1.service.UserNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserNoteServiceImpl implements UserNoteService {
    
    private final UserNoteRepository userNoteRepository;
    private final UserRepository userRepository;
    private final ClassicTextRepository classicTextRepository;
    
    @Autowired
    public UserNoteServiceImpl(
            UserNoteRepository userNoteRepository,
            UserRepository userRepository,
            ClassicTextRepository classicTextRepository) {
        this.userNoteRepository = userNoteRepository;
        this.userRepository = userRepository;
        this.classicTextRepository = classicTextRepository;
    }
    
    @Override
    @Transactional
    public UserNoteDto createNote(Long userId, Long classicTextId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        ClassicText classicText = classicTextRepository.findById(classicTextId)
                .orElseThrow(() -> new RuntimeException("条文不存在"));
        
        UserNote userNote = new UserNote();
        userNote.setUser(user);
        userNote.setClassicText(classicText);
        userNote.setContent(content);
        userNote.setCreatedAt(LocalDateTime.now());
        
        UserNote savedNote = userNoteRepository.save(userNote);
        return mapToDto(savedNote);
    }
    
    @Override
    @Transactional
    public UserNoteDto updateNote(Long userId, Long noteId, String content) {
        UserNote userNote = userNoteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("笔记不存在"));
        
        if (!userNote.getUser().getId().equals(userId)) {
            throw new RuntimeException("无权修改此笔记");
        }
        
        userNote.setContent(content);
        userNote.setUpdatedAt(LocalDateTime.now());
        
        UserNote updatedNote = userNoteRepository.save(userNote);
        return mapToDto(updatedNote);
    }
    
    @Override
    @Transactional
    public void deleteNote(Long userId, Long noteId) {
        UserNote userNote = userNoteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("笔记不存在"));
        
        if (!userNote.getUser().getId().equals(userId)) {
            throw new RuntimeException("无权删除此笔记");
        }
        
        userNoteRepository.delete(userNote);
    }
    
    @Override
    public Page<UserNoteDto> getUserNotes(Long userId, Pageable pageable) {
        return userNoteRepository.findByUserId(userId, pageable).map(this::mapToDto);
    }
    
    @Override
    public List<UserNoteDto> getUserNotesByClassicText(Long userId, Long classicTextId) {
        return userNoteRepository.findByUserIdAndClassicTextId(userId, classicTextId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    private UserNoteDto mapToDto(UserNote userNote) {
        UserNoteDto dto = new UserNoteDto();
        dto.setId(userNote.getId());
        dto.setContent(userNote.getContent());
        dto.setCreatedAt(userNote.getCreatedAt());
        dto.setUpdatedAt(userNote.getUpdatedAt());
        dto.setUserId(userNote.getUser().getId());
        dto.setTextId(userNote.getClassicText().getId());
        dto.setTextTitle(userNote.getClassicText().getTitle());
        return dto;
    }
}