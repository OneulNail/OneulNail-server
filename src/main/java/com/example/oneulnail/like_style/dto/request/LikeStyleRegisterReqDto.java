package com.example.oneulnail.like_style.dto.request;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString


public class LikeStyleReqDto {
    private Long id;
    private String style1;
    private String style2;
    private String style3;
}
