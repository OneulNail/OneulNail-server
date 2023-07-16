package com.example.oneulnail.domain.post.mapper;

import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostRegisterResDto PostRegisterEntityToDto(Post post) {
        return PostRegisterResDto.builder()
                .msg("등록 성공")
                .build();
    }
}
