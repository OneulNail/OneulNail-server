package com.example.oneulnail.domain.post.controller;

import com.example.oneulnail.domain.post.dto.request.PostRegisterReqDto;
import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.service.PostService;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public BaseResponse<PostRegisterResDto> register(@RequestBody PostRegisterReqDto postRegisterReqDto) {
        PostRegisterResDto postRegisterResDto = postService.register(postRegisterReqDto);
        return BaseResponse.onSuccess(postRegisterResDto);
    }
}
