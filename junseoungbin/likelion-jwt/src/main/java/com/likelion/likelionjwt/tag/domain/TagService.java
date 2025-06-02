package com.likelion.likelionjwt.tag.domain;

import com.likelion.likelionjwt.common.exception.BusinessException;
import com.likelion.likelionjwt.common.error.ErrorCode;
import com.likelion.likelionjwt.tag.api.dto.request.TagSaveRequestDto;
import com.likelion.likelionjwt.tag.api.dto.request.TagUpdateRequestDto;
import com.likelion.likelionjwt.tag.api.dto.response.TagInfoResponseDto;
import com.likelion.likelionjwt.tag.api.dto.response.TagListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    // 태그 생성
    public TagInfoResponseDto create(TagSaveRequestDto request) {
        Tag tag = Tag.builder()
                .name(request.getName())
                .build();
        Tag saved = tagRepository.save(tag);
        return TagInfoResponseDto.from(saved);
    }

    // 전체 태그 조회
    public TagListResponseDto findAll() {
        List<TagInfoResponseDto> tagList = tagRepository.findAll().stream()
                .map(TagInfoResponseDto::from)
                .collect(Collectors.toList());

        return TagListResponseDto.builder()
                .tags(tagList)
                .build();
    }

    // 단일 태그 조회
    public TagInfoResponseDto findById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION, "ID = " + id));
        return TagInfoResponseDto.from(tag);
    }

    // 태그 수정
    public TagInfoResponseDto update(Long id, TagUpdateRequestDto request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION, "ID = " + id));
        tag.updateName(request.getName());
        return TagInfoResponseDto.from(tag);
    }

    // 태그 삭제
    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND_EXCEPTION, "ID = " + id));
        tagRepository.delete(tag);
    }
}
