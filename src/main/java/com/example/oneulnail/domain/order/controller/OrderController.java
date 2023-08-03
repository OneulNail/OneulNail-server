package com.example.oneulnail.domain.order.controller;

import com.example.oneulnail.domain.order.dto.request.OrderRegisterReqDto;
import com.example.oneulnail.domain.order.dto.response.OrderInfoResDto;
import com.example.oneulnail.domain.order.dto.response.OrderRegisterResDto;
import com.example.oneulnail.domain.order.service.OrderService;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping // 주문생성
    public BaseResponse<OrderRegisterResDto> register(@RequestBody OrderRegisterReqDto orderRegisterReqDto){
        OrderRegisterResDto orderRegisterResDto = orderService.register(orderRegisterReqDto);
        return BaseResponse.onSuccess(orderRegisterResDto);
    }

    @GetMapping // 주문조회 by 내 주문정보
    public BaseResponse<Slice<OrderInfoResDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Slice<OrderInfoResDto> orders = orderService.findAll(pageable);
        return BaseResponse.onSuccess(orders);
    }

    @GetMapping("/status/{status}") // 주문조회 by 주문상태
    public BaseResponse<Slice<OrderInfoResDto>> findAllByStatus(
            @PathVariable("status") String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Slice<OrderInfoResDto> orders = orderService.findAllByStatus(status,pageable);
        return BaseResponse.onSuccess(orders);
    }


    @PatchMapping("/{orderId}") // 주문취소
    public BaseResponse<OrderRegisterResDto> cancelOrder(
            @PathVariable("orderId") Long orderId){
                OrderRegisterResDto orders = orderService.cancelOrder(orderId);
                return BaseResponse.onSuccess(orders);
    }
}
