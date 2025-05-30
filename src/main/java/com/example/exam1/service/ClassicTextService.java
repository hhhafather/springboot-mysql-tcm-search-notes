package com.example.exam1.service;

import com.example.exam1.dto.ClassicTextDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassicTextService {
    Page<ClassicTextDto> getAllTexts(Pageable pageable);
    ClassicTextDto getTextById(Long id);
    Page<ClassicTextDto> getTextsByCategory(String category, Pageable pageable);
    Page<ClassicTextDto> searchTexts(String keyword, Pageable pageable);
    ClassicTextDto createText(ClassicTextDto textDto);
    ClassicTextDto updateText(Long id, ClassicTextDto textDto);
    void deleteText(Long id);
}