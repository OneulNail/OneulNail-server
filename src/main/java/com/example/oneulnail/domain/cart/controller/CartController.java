package com.example.oneulnail.domain.cart.controller;

import com.example.oneulnail.domain.cart.dto.request.CartAddReqDto;
import com.example.oneulnail.domain.cart.dto.response.CartInfoResDto;
import com.example.oneulnail.domain.cart.entity.Cart;
import com.example.oneulnail.domain.cart.service.CartService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public BaseResponse<String> addToCart (
            @LoginUser User user,
            @RequestBody CartAddReqDto cartAddReqDto){
        cartService.addToCart(user,cartAddReqDto);
        return BaseResponse.onSuccess("성공");
    }

    @GetMapping
    public BaseResponse<Slice<CartInfoResDto>> findByCartId(
            @LoginUser User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Slice<CartInfoResDto> cartInfoResDtoSlice = cartService.findCartByUserIdSlice(user,pageable);
        return BaseResponse.onSuccess(cartInfoResDtoSlice);
    }

    @DeleteMapping("/{cartId}")
    public BaseResponse<String> deleteCart(
            @LoginUser User user,
            @PathVariable("cartId") Long cartId){
        cartService.deleteCart(user,cartId);
        return BaseResponse.onSuccess("CartId "+cartId +" 삭제 완료");
    }

    @DeleteMapping
    public BaseResponse<String> deleteCarts(@LoginUser User user){
        cartService.deleteCarts(user);
        return BaseResponse.onSuccess("장바구니를 모두 비웠습니다.");
    }
}
