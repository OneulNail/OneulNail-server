package com.example.oneulnail.domain.shop.service;

import com.example.oneulnail.domain.shop.dto.request.ShopRegisterReqDto;
import com.example.oneulnail.domain.shop.dto.response.ShopRegisterResDto;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.shop.mapper.ShopMapper;
import com.example.oneulnail.domain.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopRegisterResDto register(ShopRegisterReqDto requestDto) {
        Shop newShop = createShop(requestDto);
        return saveAndReturnResponse(newShop);
    }

    private Shop createShop(ShopRegisterReqDto requestDto) {
        return Shop.builder()
                .name(requestDto.getName())
                .phoneNumber(requestDto.getPhoneNumber())
                .location(requestDto.getLocation())
                .operatingHours(requestDto.getOperatingHours())
                .likesCount(0)
                .imgUrl(requestDto.getImgUrl())
                .basePrice(requestDto.getBasePrice())
                .shopInfo(requestDto.getShopInfo())
                .build();
    }

    private ShopRegisterResDto saveAndReturnResponse(Shop shop) {
        Shop savedShop = shopRepository.save(shop);
        return shopMapper.ShopRegisterEntityToDto(savedShop);
    }
}
