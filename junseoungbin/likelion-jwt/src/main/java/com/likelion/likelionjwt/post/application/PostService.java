package com.likelion.likelionjwt.post.application;

import com.likelion.likelionjwt.common.error.ErrorCode;
import com.likelion.likelionjwt.common.exception.BusinessException;
import com.likelion.likelionjwt.member.domain.Member;
import com.likelion.likelionjwt.member.domain.repository.MemberRepository;
import com.likelion.likelionjwt.post.api.dto.request.PostSaveRequestDto;
import com.likelion.likelionjwt.post.api.dto.request.PostUpdateRequestDto;
import com.likelion.likelionjwt.post.api.dto.response.PostInfoResponseDto;
import com.likelion.likelionjwt.post.api.dto.response.PostListResponseDto;
import com.likelion.likelionjwt.post.domain.Post;
import com.likelion.likelionjwt.post.domain.repository.PostRepository;
import com.likelion.tag.domain.Tag;
import com.likelion.tag.domain.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    // 게시물 저장
    @Transactional
    public void postSave(PostSaveRequestDto postSaveRequestDto, Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage()));

        Post post = Post.builder()
                .title(postSaveRequestDto.title())
                .content(postSaveRequestDto.content())
                .member(member)
                .build();

        // 태그 연결
        List<Tag> tags = tagRepository.findAllById(postSaveRequestDto.tagIds());
        tags.forEach(post::addTag);

        postRepository.save(post);
    }

    // 전체 게시글 조회
    public PostListResponseDto postFindAll() {
        List<Post> posts = postRepository.findAll();
        List<PostInfoResponseDto> postInfoResponseDtos = posts.stream()
                .map(PostInfoResponseDto::from)
                .toList();

        return PostListResponseDto.from(postInfoResponseDtos);
    }

    // 특정 작성자의 게시글 조회
    public PostListResponseDto postFindMember(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION,
                        ErrorCode.MEMBER_NOT_FOUND_EXCEPTION.getMessage() + memberId));

        List<Post> posts = postRepository.findByMember(member);
        List<PostInfoResponseDto> postInfoResponseDtos = posts.stream()
                .map(PostInfoResponseDto::from)
                .toList();

        return PostListResponseDto.from(postInfoResponseDtos);
    }

    // 게시물 수정
    @Transactional
    public void postUpdate(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND_EXCEPTION,
                        ErrorCode.POST_NOT_FOUND_EXCEPTION.getMessage() + postId));

        // 게시물 기본 내용 수정
        post.update(postUpdateRequestDto);

        // 기존 태그 제거 및 새 태그 연결
        post.clearTags();
        List<Tag> tags = tagRepository.findAllById(postUpdateRequestDto.tagIds());
        tags.forEach(post::addTag);
    }

    // 게시물 삭제
    @Transactional
    public void postDelete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException(ErrorCode.POST_NOT_FOUND_EXCEPTION,
                        ErrorCode.POST_NOT_FOUND_EXCEPTION.getMessage() + postId));
        postRepository.delete(post);
    }
}
