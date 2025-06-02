package com.likelion.likelionjwt.post.domain;

import com.likelion.likelionjwt.member.domain.Member;
import com.likelion.likelionjwt.post.api.dto.request.PostUpdateRequestDto;
import com.likelion.likelionjwt.posttag.domain.PostTag;
import com.likelion.likelionjwt.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Builder
    private Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(PostUpdateRequestDto postUpdateRequestDto) {
        this.title = postUpdateRequestDto.title();
        this.content = postUpdateRequestDto.content();
    }

    public List<Tag> getTags() {
        return postTags.stream()
                .map(PostTag::getTag)
                .collect(Collectors.toList());
    }

    public void addTag(Tag tag) {
        PostTag postTag = PostTag.builder()
                .post(this)
                .tag(tag)
                .build();
        postTags.add(postTag);
        tag.getPostTags().add(postTag);
    }

    public void clearTags() {
        for (PostTag postTag : postTags) {
            postTag.getTag().getPostTags().remove(postTag);
            postTag.setPost(null);
            postTag.setTag(null);
        }
        postTags.clear();
    }
}
