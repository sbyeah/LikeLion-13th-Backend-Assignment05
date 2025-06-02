package com.likelion.likelionjwt.tag.api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TagListResponseDto {
    private List<TagInfoResponseDto> tags;

    @Builder
    public TagListResponseDto(List<TagInfoResponseDto> tags) {
        this.tags = tags;
    }
}
