package com.example.exam1.controller;

import com.example.exam1.dto.ClassicTextDto;
import com.example.exam1.dto.response.PageResponse;
import com.example.exam1.service.ClassicTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/texts")
public class ClassicTextController {
    
    @Autowired
    private ClassicTextService classicTextService;
    
    @GetMapping
    public ResponseEntity<PageResponse<ClassicTextDto>> getAllTexts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<ClassicTextDto> textPage = classicTextService.getAllTexts(PageRequest.of(page, size));
        
        PageResponse<ClassicTextDto> response = new PageResponse<>(
                textPage.getContent(),
                textPage.getTotalElements(),
                textPage.getTotalPages(),
                textPage.getSize(),
                textPage.getNumber(),
                textPage.isFirst(),
                textPage.isLast(),
                textPage.isEmpty()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClassicTextDto> getTextById(@PathVariable Long id) {
        ClassicTextDto textDto = classicTextService.getTextById(id);
        return ResponseEntity.ok(textDto);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<PageResponse<ClassicTextDto>> getTextsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<ClassicTextDto> textPage = classicTextService.getTextsByCategory(category, PageRequest.of(page, size));
        
        PageResponse<ClassicTextDto> response = new PageResponse<>(
                textPage.getContent(),
                textPage.getTotalElements(),
                textPage.getTotalPages(),
                textPage.getSize(),
                textPage.getNumber(),
                textPage.isFirst(),
                textPage.isLast(),
                textPage.isEmpty()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<PageResponse<ClassicTextDto>> searchTexts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<ClassicTextDto> textPage = classicTextService.searchTexts(keyword, PageRequest.of(page, size));
        
        PageResponse<ClassicTextDto> response = new PageResponse<>(
                textPage.getContent(),
                textPage.getTotalElements(),
                textPage.getTotalPages(),
                textPage.getSize(),
                textPage.getNumber(),
                textPage.isFirst(),
                textPage.isLast(),
                textPage.isEmpty()
        );
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClassicTextDto> createText(@RequestBody ClassicTextDto textDto) {
        ClassicTextDto createdText = classicTextService.createText(textDto);
        return ResponseEntity.ok(createdText);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ClassicTextDto> updateText(
            @PathVariable Long id,
            @RequestBody ClassicTextDto textDto) {
        ClassicTextDto updatedText = classicTextService.updateText(id, textDto);
        return ResponseEntity.ok(updatedText);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteText(@PathVariable Long id) {
        classicTextService.deleteText(id);
        return ResponseEntity.ok().build();
    }
}