package com.likelion.likelionjwt.post.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PostUpdateRequestDto(
        @NotBlank(message = "제목은 필수로 입력해야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수로 입력해야 합니다.")
        String content,

        @NotNull(message = "태그 ID 목록은 비어 있을 수 없습니다.")
        List<Long> tagIds
) {
}
