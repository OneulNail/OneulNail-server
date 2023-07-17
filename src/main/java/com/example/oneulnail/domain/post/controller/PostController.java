package com.example.oneulnail.domain.post.controller;

import com.example.oneulnail.domain.post.dto.request.PostRegisterReqDto;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
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
    public BaseResponse<PostInfoResDto> findByPostId(@PathVariable Long postId) {
        PostInfoResDto postFindOneResDto = postService.findDtoById(postId);
        return BaseResponse.onSuccess(postFindOneResDto);
    }

    @GetMapping
    public BaseResponse<Slice<PostInfoResDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostInfoResDto> posts = postService.findAll(pageable);
        return BaseResponse.onSuccess(posts);
    }

    @GetMapping("/shop/{shopId}")
    public BaseResponse<Slice<PostInfoResDto>> findAllByShopId(
            @PathVariable Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostInfoResDto> posts = postService.findAllByShopId(shopId, pageable);
        return BaseResponse.onSuccess(posts);
    }

    @GetMapping("/category/{category}")
    public BaseResponse<Slice<PostInfoResDto>> findAllByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostInfoResDto> posts = postService.findAllByCategory(category, pageable);
        return BaseResponse.onSuccess(posts);
    }
}
