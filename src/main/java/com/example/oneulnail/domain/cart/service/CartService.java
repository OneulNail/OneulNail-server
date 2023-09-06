package com.example.oneulnail.domain.cart.service;

import com.example.oneulnail.domain.cart.dto.request.CartAddReqDto;
import com.example.oneulnail.domain.cart.dto.response.CartInfoResDto;
import com.example.oneulnail.domain.cart.entity.Cart;
import com.example.oneulnail.domain.cart.exception.NotFoundCartEntityException;
import com.example.oneulnail.domain.cart.mapper.CartMapper;
import com.example.oneulnail.domain.cart.repository.CartRepository;
import com.example.oneulnail.domain.product.entity.Product;
import com.example.oneulnail.domain.product.service.ProductService;
import com.example.oneulnail.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;

    @Transactional
    public void addToCart(User foundUser, CartAddReqDto cartAddReqDto) {
        Product foundProduct = findProduct(cartAddReqDto);
        saveNewCart(cartAddReqDto,foundUser,foundProduct);
    }

    @Transactional(readOnly = true)
    public Slice<CartInfoResDto> findCartByUserIdSlice(User foundUser, Pageable pageable) {
        Slice<Cart> carts = findCartByUserId(foundUser,pageable);
        return carts.map(cart->cartMapper.cartEntityToCartInfo(cart));
    }
    @Transactional
    public void deleteCart(User user, Long cartId) {
        Cart cart = findByCartId(user,cartId);
        removeCart(cart);
    }
    @Transactional
    public void deleteCarts(User user) {
        Slice<Cart> carts = findCartByUserId(user,null);
        carts.forEach(this::removeCart);
    }

    public Slice<Cart> findCartByUserId(User foundUser, Pageable pageable){
        return cartRepository.findCartByUserIdSlice(foundUser.getId(),pageable);
    }

    private Product findProduct(CartAddReqDto cartAddReqDto){
        Product foundProduct = productService.findById(cartAddReqDto.getProductId());
        return foundProduct;
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
        return cartRepository.findCartByUserId(user.getId(), cartId).orElseThrow(NotFoundCartEntityException::new);
    }

    private void removeCart(Cart cart){
        cartRepository.delete(cart);
    }

    public List<Cart> findCartByUserIdNotOrder(User user){
        return cartRepository.findCartByUserIdNotOrder(user.getId());
    }
}
