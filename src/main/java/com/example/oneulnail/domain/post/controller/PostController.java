package com.example.oneulnail.domain.post.controller;

import com.example.oneulnail.domain.post.dto.request.PostRegisterReqDto;
import com.example.oneulnail.domain.post.dto.response.PostInfoResDto;
import com.example.oneulnail.domain.post.dto.response.PostRegisterResDto;
import com.example.oneulnail.domain.post.service.PostService;
import com.example.oneulnail.global.response.ResultCode;
import com.example.oneulnail.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 등록")
    @PostMapping
    public ResponseEntity<ResultResponse> register(@RequestBody PostRegisterReqDto postRegisterReqDto) {
        PostRegisterResDto postRegisterResDto = postService.register(postRegisterReqDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_CREATE_SUCCESS, postRegisterResDto));
    }

    @Operation(summary = "게시글 단일 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<ResultResponse> findByPostId(@PathVariable Long postId) {
        PostInfoResDto postFindOneResDto = postService.findDtoById(postId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ONE_POST_SUCCESS, postFindOneResDto));
    }

    @Operation(summary = "게시글 전체 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostInfoResDto> posts = postService.findAll(pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_POST_SUCCESS, posts));
    }

    @Operation(summary = "가게별 게시글 조회")
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ResultResponse> findAllByShopId(
            @PathVariable Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostInfoResDto> posts = postService.findAllByShopId(shopId, pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_POST_BY_SHOP_SUCCESS, posts));
    }

    @Operation(summary = "카테고리별 게시글 조회")
    @GetMapping("/category/{category}")
    public ResponseEntity<ResultResponse> findAllByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<PostInfoResDto> posts = postService.findAllByCategory(category, pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_POST_BY_CATEGORY_SUCCESS, posts));
    }
}
