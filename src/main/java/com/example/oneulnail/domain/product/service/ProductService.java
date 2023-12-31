package com.example.oneulnail.domain.product.service;

import com.example.oneulnail.domain.product.dto.request.ProductRegisterReqDto;
import com.example.oneulnail.domain.product.dto.response.ProductInfoResDto;
import com.example.oneulnail.domain.product.dto.response.ProductRegisterResDto;
import com.example.oneulnail.domain.product.entity.Product;
import com.example.oneulnail.domain.product.exception.NotFoundProductEntityException;
import com.example.oneulnail.domain.product.mapper.ProductMapper;
import com.example.oneulnail.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductRegisterResDto register(ProductRegisterReqDto productRegisterReqDto, String imageUrl) {
        Product newProduct = buildProduct(productRegisterReqDto,imageUrl);
        Product registerProduct = productRepository.save(newProduct);
        return productMapper.productEntityToDto(registerProduct);
    }

    private Product buildProduct(ProductRegisterReqDto productRegisterReqDto,String imageUrl){
        return Product.builder()
                .name(productRegisterReqDto.getName())
                .imgUrl(imageUrl)
                .price(productRegisterReqDto.getPrice())
                .likeCount(0)
                .category(productRegisterReqDto.getCategory())
                .build();
    }

    @Transactional(readOnly = true)
    public ProductInfoResDto findDtoById(Long productId) {
        Product foundProduct = findById(productId);
        return productMapper.productInfoEntityToDto(foundProduct);
    }

    @Transactional(readOnly = true)
    public Product findById(Long productId){
        return productRepository.findById(productId).orElseThrow(NotFoundProductEntityException::new);
    }
    @Transactional(readOnly = true)
    public Slice<ProductInfoResDto> findAll(Pageable pageable) {
        Slice<Product> products = productRepository.findAllSlice(pageable);
        return products.map(product -> productMapper.productInfoEntityToDto(product));
    }
}
