package com.example.oneulnail.domain.likeStyle.dto.request;

import lombok.*;

@Getter
@Builder
public class LikeStyleReqDto {
    private Long id;
    private String style1;
    private String style2;
    private String style3;
}
