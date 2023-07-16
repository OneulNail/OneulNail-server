package com.example.oneulnail.like_style.mapper;

import com.example.oneulnail.like_style.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.like_style.entity.LikeStyle;
import org.springframework.stereotype.Component;

@Component
public class LikeStyleMapper {
    public LikeStyleRegisterResDto likeStyleRegisterEntityToDto(LikeStyle likeStyle){
        return LikeStyleRegisterResDto.builder()
                .msg("성공")
                .build();
    }

}
