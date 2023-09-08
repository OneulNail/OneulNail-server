package com.example.oneulnail.domain.shop.service;

import com.example.oneulnail.domain.shop.dto.request.ShopRegisterReqDto;
import com.example.oneulnail.domain.shop.dto.response.ShopListResDto;
import com.example.oneulnail.domain.shop.dto.response.ShopFindOneResDto;
import com.example.oneulnail.domain.shop.dto.response.ShopRegisterResDto;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.shop.exception.NotFoundShopEntityException;
import com.example.oneulnail.domain.shop.mapper.ShopMapper;
import com.example.oneulnail.domain.shop.repository.ShopRepository;

import com.example.oneulnail.global.constants.S3Upload;
//import com.example.oneulnail.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;
    private final S3Upload s3Upload;

    @Transactional
    public ShopRegisterResDto register(ShopRegisterReqDto requestDto,String imageUrl) throws IOException {
        Shop newShop = buildShop(requestDto,imageUrl);
        return saveAndReturnResponse(newShop);
    }

    private Shop buildShop(ShopRegisterReqDto shopRegisterReqDto, String imageUrl) {
        return Shop.builder()
                .name(shopRegisterReqDto.getName())
                .phoneNumber(shopRegisterReqDto.getPhoneNumber())
                .location(shopRegisterReqDto.getLocation())
                .operatingHours(shopRegisterReqDto.getOperatingHours())
                .likesCount(0)
                .imgUrl(imageUrl)
                .basePrice(shopRegisterReqDto.getBasePrice())
                .shopInfo(shopRegisterReqDto.getShopInfo())
                .build();
    }

    private ShopRegisterResDto saveAndReturnResponse(Shop shop) {
        Shop registeredShop = shopRepository.save(shop);
        return shopMapper.ShopRegisterEntityToDto(registeredShop);
    }

    @Transactional(readOnly = true)
    public Shop findById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(NotFoundShopEntityException::new);
    }

    @Transactional(readOnly = true)
    public ShopFindOneResDto findDtoById(Long shopId) {
        Shop foundShop = findById(shopId);
        return shopMapper.shopFindOneEntityToDto(foundShop);
    }

    @Transactional(readOnly = true)
    public Slice<ShopListResDto> findAll(Pageable pageable) {
        Slice<Shop> shops = shopRepository.findAllSlice(pageable);
        return shops.map(shop -> shopMapper.entityToShopListResDto(shop));
    }
}
