package com.likelion.tag.domain;

import com.likelion.posttag.domain.PostTag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Builder
    public Tag(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
