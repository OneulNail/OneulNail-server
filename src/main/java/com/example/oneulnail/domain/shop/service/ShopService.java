package com.example.oneulnail.domain.shop.service;

import com.example.oneulnail.domain.shop.dto.request.ShopRegisterReqDto;
import com.example.oneulnail.domain.shop.dto.response.ShopFindOneResDto;
import com.example.oneulnail.domain.shop.dto.response.ShopRegisterResDto;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.shop.mapper.ShopMapper;
import com.example.oneulnail.domain.shop.repository.ShopRepository;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopRegisterResDto register(ShopRegisterReqDto requestDto) {
        Shop newShop = buildShop(requestDto);
        return saveAndReturnResponse(newShop);
    }

    private Shop buildShop(ShopRegisterReqDto shopRegisterReqDto) {
        return Shop.builder()
                .name(shopRegisterReqDto.getName())
                .phoneNumber(shopRegisterReqDto.getPhoneNumber())
                .location(shopRegisterReqDto.getLocation())
                .operatingHours(shopRegisterReqDto.getOperatingHours())
                .likesCount(0)
                .imgUrl(shopRegisterReqDto.getImgUrl())
                .basePrice(shopRegisterReqDto.getBasePrice())
                .shopInfo(shopRegisterReqDto.getShopInfo())
                .build();
    }

    private ShopRegisterResDto saveAndReturnResponse(Shop shop) {
        Shop registeredShop = shopRepository.save(shop);
        return shopMapper.ShopRegisterEntityToDto(registeredShop);
    }

    public ShopFindOneResDto findById(Long shopId) {
        Shop foundShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException("Shop not found"));
        return shopMapper.shopFindOneEntityToDto(foundShop);
    }
}
