package com.example.oneulnail.like_style.controller;

import com.example.oneulnail.global.entity.BaseResponse;
import com.example.oneulnail.like_style.dto.request.LikeStyleRegisterReqDto;
import com.example.oneulnail.like_style.dto.response.LikeStyleRegisterResDto;
import com.example.oneulnail.like_style.service.LikeStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/like-style")
@RequiredArgsConstructor
public class LikeStyleController {
    private final LikeStyleService likeStyleService;
    @PostMapping
    public BaseResponse<LikeStyleRegisterResDto> register(@RequestBody LikeStyleRegisterReqDto likeStyleRegisterReqDto){
        LikeStyleRegisterResDto likeStyleRegisterResDto = likeStyleService.register(likeStyleRegisterReqDto);
        return BaseResponse.onSuccess(likeStyleRegisterResDto);
    }

}
