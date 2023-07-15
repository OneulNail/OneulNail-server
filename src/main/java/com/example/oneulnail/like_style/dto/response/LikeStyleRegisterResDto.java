package com.example.oneulnail.like_style.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LikeStyleResDto {
    private Long id;
    private String style1;
    private String style2;
    private String style3;
}
