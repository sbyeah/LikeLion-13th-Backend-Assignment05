package com.likelion.likelionjwt.tag.api.dto.response;

import com.likelion.likelionjwt.tag.domain.Tag;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TagInfoResponseDto {
    private Long id;
    private String name;

    @Builder
    public TagInfoResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagInfoResponseDto from(Tag tag) {
        return TagInfoResponseDto.builder()
                .id(tag.getTagId())
                .name(tag.getName())
                .build();
    }
}
