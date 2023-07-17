package com.example.oneulnail.domain.likeStyle.mapper;

import com.example.oneulnail.domain.likeStyle.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.likeStyle.entity.LikeStyle;
import org.springframework.stereotype.Component;

@Component
public class LikeStyleMapper {
    public LikeStyleRegisterResDto likeStyleRegisterEntityToDto(LikeStyle likeStyle){
        return LikeStyleRegisterResDto.builder()
                .msg("성공")
                .build();
    }
}
