package com.example.oneulnail.domain.product.controller;

import com.example.oneulnail.domain.product.dto.request.ProductRegisterReqDto;
import com.example.oneulnail.domain.product.dto.response.ProductInfoResDto;
import com.example.oneulnail.domain.product.dto.response.ProductRegisterResDto;
import com.example.oneulnail.domain.product.service.ProductService;
import com.example.oneulnail.global.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductCotroller {

    private final ProductService productService;

    @Operation(summary = "상품 등록")
    @PostMapping
    public BaseResponse<ProductRegisterResDto> productRegister(@RequestBody ProductRegisterReqDto productRegisterReqDto){
        ProductRegisterResDto productRegisterResDto = productService.register(productRegisterReqDto);
        return BaseResponse.onSuccess(productRegisterResDto);
    }

    @Operation(summary = "상품 단일 조회")
    @GetMapping("/{productId}")
    public BaseResponse<ProductInfoResDto> productInfo(@PathVariable Long productId){
        ProductInfoResDto productInfoResDto = productService.findDtoById(productId);
        return BaseResponse.onSuccess(productInfoResDto);
    }

    @Operation(summary = "상품 전체 조회")
    @GetMapping
    public BaseResponse<Slice<ProductInfoResDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ProductInfoResDto> posts = productService.findAll(pageable);
        return BaseResponse.onSuccess(posts);
    }


}
