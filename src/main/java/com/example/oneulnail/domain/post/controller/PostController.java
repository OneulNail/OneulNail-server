package com.example.oneulnail.domain.post.controller;

import com.example.oneulnail.domain.post.dto.request.PostRegisterReqDto;
import com.example.oneulnail.domain.post.dto.response.PostFindOneResDto;
import com.example.oneulnail.domain.post.dto.response.PostListResDto;
import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.service.PostService;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @GetMapping
    public BaseResponse<Slice<PostListResDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostListResDto> posts = postService.findAll(pageable);
        return BaseResponse.onSuccess(posts);
    }
}
