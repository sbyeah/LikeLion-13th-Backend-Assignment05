package com.likelion.likelionjwt.post.api.dto.response;

import com.likelion.likelionjwt.post.domain.Post;
import lombok.Builder;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record PostInfoResponseDto(
        String title,
        String content,
        String writer,
        List<String> tags
) {
    public static PostInfoResponseDto from(Post post) {
        return PostInfoResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getMember().getName())
                .tags(post.getTags().stream()
                        .map(tag -> tag.getName())
                        .collect(Collectors.toList()))
                .build();
    }
}
