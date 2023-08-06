package com.example.oneulnail.domain.order.controller;

import com.example.oneulnail.domain.order.dto.request.OrderRegisterReqDto;
import com.example.oneulnail.domain.order.dto.response.OrderInfoResDto;
import com.example.oneulnail.domain.order.dto.response.OrderRegisterResDto;
import com.example.oneulnail.domain.order.entity.Order;
import com.example.oneulnail.domain.order.entity.OrderStatus;
import com.example.oneulnail.domain.order.service.OrderService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.global.entity.BaseResponse;
import com.example.oneulnail.global.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;

@Tag(name = "주문")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성")
    @PostMapping // 주문생성
    public BaseResponse<OrderRegisterResDto> register(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody OrderRegisterReqDto orderRegisterReqDto){
        OrderRegisterResDto orderRegisterResDto = orderService.register(user, orderRegisterReqDto);
        return BaseResponse.onSuccess(orderRegisterResDto);
    }

    @Operation(summary = "내 주문 정보 조회")
    @GetMapping // 주문조회 by 내 주문정보
    public BaseResponse<Slice<OrderInfoResDto>> findAll(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Slice<OrderInfoResDto> orders = orderService.findAll(user,pageable);
        return BaseResponse.onSuccess(orders);
    }

    @Operation(summary = "주문상태별 주문 조회")
    @GetMapping("/status/{status}") // 주문조회 by 주문상태
    public BaseResponse<Slice<OrderInfoResDto>> findAllByStatus(
            @Parameter(hidden = true) @LoginUser User user,
            @PathVariable("status") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        Pageable pageable = PageRequest.of(page, size);
        Slice<OrderInfoResDto> orders = orderService.findAllByStatus(user,orderStatus,pageable);
        return BaseResponse.onSuccess(orders);
    }

    @Operation(summary = "주문 취소")
    @PostMapping("/cancel/{orderId}") // 주문취소
    public BaseResponse<String> cancelOrder(
            @PathVariable("orderId") Long orderId){
            orderService.cancelOrder(orderId);
            return BaseResponse.onSuccess("주문이 취소되었습니다.");
    }
}
