package com.example.oneulnail.domain.cart.controller;

import com.example.oneulnail.domain.cart.dto.request.CartAddReqDto;
import com.example.oneulnail.domain.cart.dto.response.CartInfoResDto;
import com.example.oneulnail.domain.cart.service.CartService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.global.response.ResultCode;
import com.example.oneulnail.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니 등록")
    @PostMapping
    public ResponseEntity<ResultResponse> addToCart (
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody CartAddReqDto cartAddReqDto){
        cartService.addToCart(user,cartAddReqDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CART_CREATE_SUCCESS, "장바구니 생성 성공"));
    }

    @Operation(summary = "장바구니 전체 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> findCartsByUser(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Slice<CartInfoResDto> cartInfoResDtoSlice = cartService.findCartByUserIdSlice(user,pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_CART_SUCCESS, cartInfoResDtoSlice));
    }

    @Operation(summary = "장바구니 단일 삭제")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ResultResponse> deleteCart(
            @Parameter(hidden = true) @LoginUser User user,
            @PathVariable("cartId") Long cartId){
        cartService.deleteCart(user,cartId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_ONE_CART_SUCCESS, "장바구니 단일 삭제 성공"));
    }

    @Operation(summary = "장바구니 전체 삭제")
    @DeleteMapping
    public ResponseEntity<ResultResponse> deleteCarts(@Parameter(hidden = true) @LoginUser User user){
        cartService.deleteCarts(user);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_ALL_CART_SUCCESS, "장바구니 전체 삭제 성공"));
   }
}
