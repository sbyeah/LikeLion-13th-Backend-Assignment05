package com.likelion.tag.api;

import com.likelion.tag.application.TagService;
import com.likelion.tag.dto.request.TagSaveRequestDto;
import com.likelion.tag.dto.request.TagUpdateRequest;
import com.likelion.tag.dto.response.TagInfoResponseDto;
import com.likelion.tag.dto.response.TagListResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // 태그 생성
    @PostMapping
    public ResponseEntity<TagInfoResponseDto> createTag(@Valid @RequestBody TagSaveRequestDto request) {
        TagInfoResponseDto response = tagService.create(request);
        return ResponseEntity.ok(response);
    }

    // 전체 태그 조회
    @GetMapping
    public ResponseEntity<TagListResponseDto> getAllTags() {
        TagListResponseDto response = tagService.findAll();
        return ResponseEntity.ok(response);
    }

    // 단건 태그 조회
    @GetMapping("/{id}")
    public ResponseEntity<TagInfoResponseDto> getTagById(@PathVariable Long id) {
        TagInfoResponseDto response = tagService.findById(id);
        return ResponseEntity.ok(response);
    }

    // 태그 수정
    @PutMapping("/{id}")
    public ResponseEntity<TagInfoResponseDto> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagUpdateRequest request) {
        TagInfoResponseDto response = tagService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // 태그 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
