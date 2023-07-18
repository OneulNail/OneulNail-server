package com.example.oneulnail.domain.reservation.mapper;

import com.example.oneulnail.domain.reservation.dto.response.ReservationInfoResDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationRegisterResDto;
import com.example.oneulnail.domain.reservation.entity.Reservation;
import com.example.oneulnail.domain.shop.entity.Shop;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationRegisterResDto reservationRegisterEntityToDto(Reservation reservation, Shop shop){
        return ReservationRegisterResDto.builder()
                .msg(shop.getName()+"에 예약이 완료되었습니다.")
                .build();
    }

    public ReservationInfoResDto reservationEntityToReservationInfo(Reservation reservation) {
        return ReservationInfoResDto.builder()
                .reservationId(reservation.getId())
                .date(reservation.getDate())
                .build();
    }
}
