package com.example.oneulnail.domain.likeStyle.controller;

import com.example.oneulnail.domain.likeStyle.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.domain.likeStyle.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.domain.likeStyle.service.LikeStyleService;
import com.example.oneulnail.global.response.ResultCode;
import com.example.oneulnail.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResultResponse> register(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody LikeStyleRegisterReqDto likeStyleRegisterReqDto){
        LikeStyleRegisterResDto likeStyleRegisterResDto = likeStyleService.register(user, likeStyleRegisterReqDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.LIKE_POST_CREATE_SUCCESS, likeStyleRegisterResDto));
    }

    @Operation(summary = "추천 네일 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> findTopThreePostsForEachStyle(
            @Parameter(hidden = true) @LoginUser User user) {
        List<PostInfoResDto> posts = likeStyleService.findTopThreePostsForEachStyle(user);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_THREE_LIKE_STYLE_SUCCESS, posts));
    }
}
