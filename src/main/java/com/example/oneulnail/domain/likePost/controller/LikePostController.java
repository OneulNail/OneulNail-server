package com.example.oneulnail.domain.likePost.controller;

import com.example.oneulnail.domain.likePost.dto.request.LikePostRegisterReqDto;
import com.example.oneulnail.domain.likePost.service.LikePostService;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/like_post")
@RequiredArgsConstructor
public class LikePostController {

    private final LikePostService likePostService;

    @PostMapping
    public BaseResponse<String> register(
            HttpServletRequest request,
            @RequestBody LikePostRegisterReqDto registerReqDto) {
        likePostService.register(request, registerReqDto);
        return BaseResponse.onSuccess("즐겨찾기 등록 성공");
    }

    @GetMapping
    public BaseResponse<Slice<PostInfoResDto>> findPostsByUserId(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostInfoResDto> postInfoResDtoSlice = likePostService.findPostsByUserId(request, pageable);
        return BaseResponse.onSuccess(postInfoResDtoSlice);
    }
}
