package com.example.oneulnail.domain.reservation.service;

import com.example.oneulnail.domain.reservation.dto.request.ReservationRegisterReqDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationRegisterResDto;
import com.example.oneulnail.domain.reservation.entity.Reservation;
import com.example.oneulnail.domain.reservation.mapper.ReservationMapper;
import com.example.oneulnail.domain.reservation.repository.ReservationRepository;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShopService shopService;
    private final ReservationMapper ReservationMapper;

    public ReservationRegisterResDto reservate(ReservationRegisterReqDto reservationRegisterReqDto) {
        Shop foundShop = shopService.findById(reservationRegisterReqDto.getShopId());
        Reservation newReservation = buildReservation(reservationRegisterReqDto,foundShop);
        return saveReservation(newReservation,foundShop);
    }

    private Reservation buildReservation(ReservationRegisterReqDto reservationRegisterReqDto,Shop shop){
        return Reservation.builder()
                .shop(shop)
                .date(reservationRegisterReqDto.getDate())
                .build();
    }

    private ReservationRegisterResDto saveReservation(Reservation reservation,Shop shop){
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.reservationRegisterEntityToDto(savedReservation,shop);
    }
}
