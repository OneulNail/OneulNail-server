package com.example.oneulnail.domain.like_style.dto.request;

import lombok.*;

@Getter
@Builder
public class LikeStyleReqDto {
    private Long id;
    private String style1;
    private String style2;
    private String style3;
}
