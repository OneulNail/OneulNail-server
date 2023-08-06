package com.example.oneulnail.domain.order.service;

import com.example.oneulnail.domain.cart.entity.Cart;
import com.example.oneulnail.domain.cart.repository.CartRepository;
import com.example.oneulnail.domain.cart.service.CartService;
import com.example.oneulnail.domain.order.dto.request.OrderRegisterReqDto;
import com.example.oneulnail.domain.order.dto.response.OrderInfoResDto;
import com.example.oneulnail.domain.order.dto.response.OrderRegisterResDto;
import com.example.oneulnail.domain.order.entity.Order;
import com.example.oneulnail.domain.order.entity.OrderStatus;
import com.example.oneulnail.domain.order.mapper.OrderMapper;
import com.example.oneulnail.domain.order.repository.OrderRepository;
import com.example.oneulnail.domain.user.controller.SignController;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartService cartService;
    private final OrderMapper orderMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);

    @Transactional
    public OrderRegisterResDto register(User user, OrderRegisterReqDto orderRegisterReqDto) {
        Order newOrder = buildOrder(orderRegisterReqDto, user);
        Order registerOrder = orderRepository.save(newOrder); //주문생성

        List<Cart> carts = cartService.findCartByUserIdNotOrder(user);
        carts.forEach(cart -> cart.updateOrder(registerOrder));
        return orderMapper.orderEntityToDto(registerOrder);
    }

    private Order buildOrder(OrderRegisterReqDto orderRegisterReqDto,User user){
        return Order.builder()
                .paymentMethod(orderRegisterReqDto.getPaymentMethod())
                .totalPrice(orderRegisterReqDto.getTotalPrice())
                .status(OrderStatus.ORDER_COMPLETED)
                .user(user)
                .build();
    }

    @Transactional(readOnly = true)
    public Slice<OrderInfoResDto> findAll(User user, Pageable pageable){
        Slice<Order> orders = orderRepository.findAllSlice(user.getId(), pageable);
        return orders.map(order -> orderMapper.orderEntityToOrderInfoDto(order));
    }

    @Transactional(readOnly = true)
    public void cancelOrder(Long orderId){
        Order order =  orderRepository.findById(orderId).orElseThrow(()->new NotFoundException("orderId Not Found"));
        order.updateStatus(OrderStatus.ORDER_CANCELED);
    }


    @Transactional(readOnly = true)
    public Slice<OrderInfoResDto> findAllByStatus(User user, OrderStatus status, Pageable pageable) {
        Slice<Order> pageResult = orderRepository.findByStatusSlice(user.getId(), status, pageable);
        return pageResult.map(order -> orderMapper.orderEntityToOrderInfoDto(order));
    }

}


