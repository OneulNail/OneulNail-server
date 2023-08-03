package com.example.oneulnail.domain.order.service;

import com.example.oneulnail.domain.order.dto.request.OrderRegisterReqDto;
import com.example.oneulnail.domain.order.dto.response.OrderInfoResDto;
import com.example.oneulnail.domain.order.dto.response.OrderRegisterResDto;
import com.example.oneulnail.domain.order.entity.Order;
import com.example.oneulnail.domain.order.mapper.OrderMapper;
import com.example.oneulnail.domain.order.repository.OrderRepository;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderRegisterResDto register(OrderRegisterReqDto orderRegisterReqDto){
        Order newOrder = buildOrder(orderRegisterReqDto);
        Order registerOrder = orderRepository.save(newOrder);
        return orderMapper.orderEntityToDto(registerOrder);
    }

    private Order buildOrder(OrderRegisterReqDto orderRegisterReqDto){
        return Order.builder()
                .paymentMethod(orderRegisterReqDto.getPaymentMethod())
                .totalPrice(orderRegisterReqDto.getTotalPrice())
                .status(orderRegisterReqDto.getStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public Slice<OrderInfoResDto> findAll(Pageable pageable){
        Slice<Order> orders = orderRepository.findAllSlice(pageable);
        return orders.map(order -> orderMapper.orderInfoEntityToDto(order));
    }

    @Transactional(readOnly = true)
    public OrderRegisterResDto cancelOrder(Long orderId){
        Order order =  orderRepository.findById(orderId).orElseThrow(()->new NotFoundException("orderId Not Found"));
        Order newOrder = buildCancelOrder(order);
        Order registerOrder = orderRepository.save(newOrder);
        return orderMapper.orderEntityToDto(registerOrder);
    }


    private Order buildCancelOrder(Order order){
        return Order.builder()
                .paymentMethod(order.getPaymentMethod())
                .totalPrice(order.getTotalPrice())
                .status("주문 취소")
                .build();
    }

    @Transactional(readOnly = true)
    public Slice<OrderInfoResDto> findAllByStatus(String status, Pageable pageable) {
        Slice<Order> pageResult = orderRepository.findByStatusSlice(status, pageable);
        return pageResult.map(order -> orderMapper.orderInfoEntityToDto(order));
    }

}


