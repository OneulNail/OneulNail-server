package com.example.oneulnail.domain.shop.controller;

import com.example.oneulnail.domain.shop.dto.request.ShopRegisterReqDto;
import com.example.oneulnail.domain.shop.dto.response.ShopFindOneResDto;
import com.example.oneulnail.domain.shop.dto.response.ShopRegisterResDto;
import com.example.oneulnail.domain.shop.service.ShopService;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public BaseResponse<ShopRegisterResDto> register(@RequestBody ShopRegisterReqDto shopRegisterReqDto) {
        ShopRegisterResDto shopRegisterResDto = shopService.register(shopRegisterReqDto);
        return BaseResponse.onSuccess(shopRegisterResDto);
    }

    @GetMapping("/{shopId}")
    public BaseResponse<ShopFindOneResDto> findByShopId(@PathVariable Long shopId) {
        ShopFindOneResDto shopFindOneResDto = shopService.findById(shopId);
        return BaseResponse.onSuccess(shopFindOneResDto);
    }
}
