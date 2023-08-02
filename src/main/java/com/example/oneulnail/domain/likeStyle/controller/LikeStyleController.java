package com.example.oneulnail.domain.likeStyle.controller;

import com.example.oneulnail.domain.likeStyle.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.global.entity.BaseResponse;
import com.example.oneulnail.domain.likeStyle.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.domain.likeStyle.service.LikeStyleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "선호스타일")
@RestController
@RequestMapping("/like_style")
@RequiredArgsConstructor
public class LikeStyleController {
    private final LikeStyleService likeStyleService;

    @Operation(summary = "선호스타일 등록")
    @PostMapping
    public BaseResponse<LikeStyleRegisterResDto> register(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody LikeStyleRegisterReqDto likeStyleRegisterReqDto){
        LikeStyleRegisterResDto likeStyleRegisterResDto = likeStyleService.register(user, likeStyleRegisterReqDto);
        return BaseResponse.onSuccess(likeStyleRegisterResDto);
    }

    @Operation(summary = "추천 네일 조회")
    @GetMapping
    public BaseResponse<List<PostInfoResDto>> findTopThreePostsForEachStyle(
            @Parameter(hidden = true) @LoginUser User user) {
        List<PostInfoResDto> posts = likeStyleService.findTopThreePostsForEachStyle(user);
        return BaseResponse.onSuccess(posts);
    }
}
