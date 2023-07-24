package com.example.oneulnail.domain.product.service;

import com.example.oneulnail.domain.product.dto.request.ProductRegisterReqDto;
import com.example.oneulnail.domain.product.dto.response.ProductInfoResDto;
import com.example.oneulnail.domain.product.dto.response.ProductRegisterResDto;
import com.example.oneulnail.domain.product.entity.Product;
import com.example.oneulnail.domain.product.mapper.ProductMapper;
import com.example.oneulnail.domain.product.repository.ProductRepository;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductRegisterResDto register(ProductRegisterReqDto productRegisterReqDto) {
        Product newProduct = buildProduct(productRegisterReqDto);
        Product registerProduct = productRepository.save(newProduct);
        return productMapper.productEntityToDto(registerProduct);
    }

    private Product buildProduct(ProductRegisterReqDto productRegisterReqDto){
        return Product.builder()
                .name(productRegisterReqDto.getName())
                .imgUrl(productRegisterReqDto.getImgUrl())
                .price(productRegisterReqDto.getPrice())
                .build();
    }
    @Transactional(readOnly = true)
    public ProductInfoResDto findDtoById(Long productId) {
        Product foundProduct = findById(productId);
        return productMapper.productInfoEntityToDto(foundProduct);
    }

    @Transactional(readOnly = true)
    public Product findById(Long productId){
        return productRepository.findById(productId).orElseThrow(()->new NotFoundException("Product Not Found"));
    }
}
