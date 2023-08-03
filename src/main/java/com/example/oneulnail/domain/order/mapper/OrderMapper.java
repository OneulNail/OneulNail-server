package com.example.oneulnail.domain.order.mapper;

import com.example.oneulnail.domain.order.dto.response.OrderInfoResDto;
import com.example.oneulnail.domain.order.dto.response.OrderRegisterResDto;
import com.example.oneulnail.domain.order.entity.Order;
import com.example.oneulnail.domain.order.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderRegisterResDto orderEntityToDto(Order registerOrder){
        return OrderRegisterResDto.builder()
                .msg("주문이 생성되었습니다.")
                .build();
    }

    public OrderInfoResDto orderInfoEntityToDto(Order order){
        return OrderInfoResDto.builder()
                .orderId(order.getId())
                .paymentMethod(order.getPaymentMethod())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
