package com.example.oneulnail.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostFindOneResDto {
    private Long shopId;
    private Long postId;
    private String name;
    private int likeCount;
    private String imgUrl;
    private int price;
    private String content;
}
