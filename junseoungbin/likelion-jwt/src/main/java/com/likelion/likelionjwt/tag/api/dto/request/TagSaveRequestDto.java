package com.likelion.likelionjwt.tag.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TagSaveRequestDto {

        @NotBlank(message = "태그 이름은 필수입니다.")
        private String name;
}
