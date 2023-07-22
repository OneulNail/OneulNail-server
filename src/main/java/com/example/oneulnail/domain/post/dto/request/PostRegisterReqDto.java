package com.example.oneulnail.domain.post.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterReqDto {

    private Long shopId;
    private String name;
    private String imgUrl;
    private int price;
    private String content;
    private String category;
}
