package com.example.oneulnail.domain.shop.controller;

import com.example.oneulnail.domain.shop.dto.request.ShopRegisterReqDto;
import com.example.oneulnail.domain.shop.dto.response.ShopListResDto;
import com.example.oneulnail.domain.shop.dto.response.ShopFindOneResDto;
import com.example.oneulnail.domain.shop.dto.response.ShopRegisterResDto;
import com.example.oneulnail.domain.shop.service.ShopService;

import com.example.oneulnail.global.response.ResultCode;
import com.example.oneulnail.global.response.ResultResponse;

import com.example.oneulnail.global.constants.S3Upload;
import com.example.oneulnail.global.entity.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "네일샵")
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final S3Upload s3Upload;

    @Operation(summary = "가게 등록")
    @PostMapping
    public ResponseEntity<ResultResponse> register(
            @RequestPart(value = "shopRegisterReq") ShopRegisterReqDto shopRegisterReqDto,
            @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
        String imageUrl = s3Upload.upload(file);
        ShopRegisterResDto shopRegisterResDto = shopService.register(shopRegisterReqDto,imageUrl);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SHOP_CREATE_SUCCESS, shopRegisterResDto));
    }

    @Operation(summary = "가게 단일 조회")
    @GetMapping("/{shopId}")
    public ResponseEntity<ResultResponse> findByShopId(@PathVariable Long shopId) {
        ShopFindOneResDto shopFindOneResDto = shopService.findDtoById(shopId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ONE_SHOP_SUCCESS, shopFindOneResDto));
    }

    @Operation(summary = "가게 전체 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ShopListResDto> shops = shopService.findAll(pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_SHOP_SUCCESS, shops));
    }
}
