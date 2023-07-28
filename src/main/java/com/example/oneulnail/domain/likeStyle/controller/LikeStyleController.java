package com.example.oneulnail.domain.likeStyle.controller;

import com.example.oneulnail.domain.likeStyle.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.global.entity.BaseResponse;
import com.example.oneulnail.domain.likeStyle.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.domain.likeStyle.service.LikeStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like_style")
@RequiredArgsConstructor
public class LikeStyleController {
    private final LikeStyleService likeStyleService;

    @PostMapping
    public BaseResponse<LikeStyleRegisterResDto> register(
            @LoginUser User user,
            @RequestBody LikeStyleRegisterReqDto likeStyleRegisterReqDto){
        LikeStyleRegisterResDto likeStyleRegisterResDto = likeStyleService.register(user, likeStyleRegisterReqDto);
        return BaseResponse.onSuccess(likeStyleRegisterResDto);
    }

    @GetMapping
    public BaseResponse<List<PostInfoResDto>> findTopThreePostsForEachStyle(@LoginUser User user) {
        List<PostInfoResDto> posts = likeStyleService.findTopThreePostsForEachStyle(user);
        return BaseResponse.onSuccess(posts);
    }
}
