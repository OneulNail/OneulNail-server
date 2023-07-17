package com.example.oneulnail.domain.post.mapper;

import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostRegisterResDto postRegisterEntityToDto(Post post) {
        return PostRegisterResDto.builder()
                .msg("등록 성공")
                .build();
    }

    public PostInfoResDto postEntityToPostInfo(Post post) {
        return PostInfoResDto.builder()
                .shopId(post.getShop().getId())
                .postId(post.getId())
                .name(post.getName())
                .likeCount(post.getLikeCount())
                .imgUrl(post.getImgUrl())
                .price(post.getPrice())
                .category(post.getCategory())
                .content(post.getContent())
                .build();
    }
}
