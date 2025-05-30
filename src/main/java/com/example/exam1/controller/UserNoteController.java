package com.example.exam1.controller;

import com.example.exam1.dto.UserNoteDto;
import com.example.exam1.dto.response.PageResponse;
import com.example.exam1.entity.User;
import com.example.exam1.repository.UserRepository;
import com.example.exam1.service.UserNoteService;
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
@RequestMapping("/api/notes")
public class UserNoteController {
    
    @Autowired
    private UserNoteService userNoteService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PageResponse<UserNoteDto>> getUserNotes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        Page<UserNoteDto> notePage = userNoteService.getUserNotes(userId, PageRequest.of(page, size));
        
        PageResponse<UserNoteDto> response = new PageResponse<>(
                notePage.getContent(),
                notePage.getTotalElements(),
                notePage.getTotalPages(),
                notePage.getSize(),
                notePage.getNumber(),
                notePage.isFirst(),
                notePage.isLast(),
                notePage.isEmpty()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/text/{textId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserNoteDto>> getUserNotesByClassicText(
            @PathVariable Long textId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        List<UserNoteDto> notes = userNoteService.getUserNotesByClassicText(userId, textId);
        return ResponseEntity.ok(notes);
    }
    
    @PostMapping("/text/{textId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserNoteDto> createNote(
            @PathVariable Long textId,
            @Valid @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        String content = payload.get("content");
        
        if (content == null) {
            return ResponseEntity.badRequest().build();
        }
        
        UserNoteDto note = userNoteService.createNote(userId, textId, content);
        return ResponseEntity.ok(note);
    }
    
    @PutMapping("/{noteId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserNoteDto> updateNote(
            @PathVariable Long noteId,
            @Valid @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        String content = payload.get("content");
        
        if (content == null) {
            return ResponseEntity.badRequest().build();
        }
        
        UserNoteDto note = userNoteService.updateNote(userId, noteId, content);
        return ResponseEntity.ok(note);
    }
    
    @DeleteMapping("/{noteId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteNote(
            @PathVariable Long noteId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Long userId = extractUserId(userDetails);
        userNoteService.deleteNote(userId, noteId);
        return ResponseEntity.ok().build();
    }
    
    private Long extractUserId(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}