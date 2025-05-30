package com.example.exam1.service.impl;

import com.example.exam1.dto.ClassicTextDto;
import com.example.exam1.entity.ClassicText;
import com.example.exam1.repository.ClassicTextRepository;
import com.example.exam1.service.ClassicTextService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClassicTextServiceImpl implements ClassicTextService {
    
    @Autowired
    private ClassicTextRepository classicTextRepository;
    
    @Override
    public Page<ClassicTextDto> getAllTexts(Pageable pageable) {
        return classicTextRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    @Override
    public ClassicTextDto getTextById(Long id) {
        return classicTextRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Classic text not found with id: " + id));
    }
    
    @Override
    public Page<ClassicTextDto> getTextsByCategory(String category, Pageable pageable) {
        return classicTextRepository.findByCategory(category, pageable)
                .map(this::convertToDto);
    }
    
    @Override
    public Page<ClassicTextDto> searchTexts(String keyword, Pageable pageable) {
        return classicTextRepository.findByContentContainingOrTitleContaining(keyword, keyword, pageable)
                .map(this::convertToDto);
    }
    
    @Override
    public ClassicTextDto createText(ClassicTextDto textDto) {
        ClassicText text = convertToEntity(textDto);
        text = classicTextRepository.save(text);
        return convertToDto(text);
    }
    
    @Override
    public ClassicTextDto updateText(Long id, ClassicTextDto textDto) {
        ClassicText text = classicTextRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classic text not found with id: " + id));
        
        text.setTitle(textDto.getTitle());
        text.setContent(textDto.getContent());
        text.setCategory(textDto.getCategory());
        text.setDifficulty(textDto.getDifficulty());
        
        text = classicTextRepository.save(text);
        return convertToDto(text);
    }
    
    @Override
    public void deleteText(Long id) {
        if (!classicTextRepository.existsById(id)) {
            throw new EntityNotFoundException("Classic text not found with id: " + id);
        }
        classicTextRepository.deleteById(id);
    }
    
    private ClassicTextDto convertToDto(ClassicText text) {
        return new ClassicTextDto(
                text.getId(),
                text.getTitle(),
                text.getContent(),
                text.getCategory(),
                text.getDifficulty(),
                text.getCreatedAt(),
                text.getUpdatedAt()
        );
    }
    
    private ClassicText convertToEntity(ClassicTextDto dto) {
        ClassicText text = new ClassicText();
        text.setTitle(dto.getTitle());
        text.setContent(dto.getContent());
        text.setCategory(dto.getCategory());
        text.setDifficulty(dto.getDifficulty());
        return text;
    }
}