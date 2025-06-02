package com.likelion.likelionjwt.posttag.domain;

import com.likelion.likelionjwt.post.domain.Post;
import com.likelion.likelionjwt.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_tag_id")
    private Long postTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Builder
    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }
}
