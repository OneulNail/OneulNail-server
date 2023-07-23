package com.example.oneulnail.domain.likeStyle.controller;

import com.example.oneulnail.domain.likeStyle.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.global.entity.BaseResponse;
import com.example.oneulnail.domain.likeStyle.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.domain.likeStyle.service.LikeStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/like_style")
@RequiredArgsConstructor
public class LikeStyleController {
    private final LikeStyleService likeStyleService;

    @PostMapping
    public BaseResponse<LikeStyleRegisterResDto> register(
            HttpServletRequest request,
            @RequestBody LikeStyleRegisterReqDto likeStyleRegisterReqDto){
        LikeStyleRegisterResDto likeStyleRegisterResDto = likeStyleService.register(request, likeStyleRegisterReqDto);
        return BaseResponse.onSuccess(likeStyleRegisterResDto);
    }

    @GetMapping
    public BaseResponse<List<PostInfoResDto>> findTopThreePostsForEachStyle(HttpServletRequest request) {
        List<PostInfoResDto> posts = likeStyleService.findTopThreePostsForEachStyle(request);
        return BaseResponse.onSuccess(posts);
    }
}
