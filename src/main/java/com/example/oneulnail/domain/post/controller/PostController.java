package com.example.oneulnail.domain.post.controller;

import com.example.oneulnail.domain.post.dto.request.PostRegisterReqDto;
import com.example.oneulnail.domain.post.dto.response.PostFindOneResDto;
import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.service.PostService;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{postId}")
    public BaseResponse<PostFindOneResDto> findByPostId(@PathVariable Long postId) {
        PostFindOneResDto postFindOneResDto = postService.findDtoById(postId);
        return BaseResponse.onSuccess(postFindOneResDto);
    }
}
