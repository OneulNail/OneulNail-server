package com.example.oneulnail.domain.cart.service;

import com.example.oneulnail.domain.cart.dto.request.CartAddReqDto;
import com.example.oneulnail.domain.cart.dto.response.CartInfoResDto;
import com.example.oneulnail.domain.cart.entity.Cart;
import com.example.oneulnail.domain.cart.mapper.CartMapper;
import com.example.oneulnail.domain.cart.repository.CartRepository;
import com.example.oneulnail.domain.product.entity.Product;
import com.example.oneulnail.domain.product.service.ProductService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductService productService;
    private final CartMapper cartMapper;
    @Transactional
    public void addToCart(User foundUser, CartAddReqDto cartAddReqDto) {
        Product foundProduct =findProduct(cartAddReqDto);
        saveNewCart(cartAddReqDto,foundUser,foundProduct);
    }

    @Transactional(readOnly = true)
    public Slice<CartInfoResDto> findCartByUserIdSlice(User foundUser, Pageable pageable) {
        Slice<Cart> carts = cartRepository.findCartByUserIdSlice(foundUser.getId(),pageable);
        return carts.map(cart->cartMapper.cartEntityToCartInfo(cart));
    }
    @Transactional
    public void deleteCart(User user, Long cartId) {
        Cart cart = findByCartId(user,cartId);
        removeCart(cart);
    }
    @Transactional
    public void deleteCarts(User user) {
        Slice<Cart> carts = cartRepository.findCartByUserIdSlice(user.getId(),null);
        carts.forEach(this::removeCart);
    }

    private Product findProduct(CartAddReqDto cartAddReqDto){
        Product product = productService.findById(cartAddReqDto.getProductId());
        return product;
    }

    private void saveNewCart(CartAddReqDto cartAddReqDto,User user,Product product){
        Cart newCart = Cart.builder()
                .quantity(cartAddReqDto.getQuantity())
                .user(user)
                .product(product)
                .build();
         cartRepository.save(newCart);
    }

    private Cart findByCartId(User user,Long cartId){
        Cart cart = cartRepository.findCartByUserId(user.getId(),cartId);
        if(cart == null) throw new NotFoundException("NotFound CartId");
        return cart;
    }

    private void removeCart(Cart cart){
        cartRepository.delete(cart);
    }
}
