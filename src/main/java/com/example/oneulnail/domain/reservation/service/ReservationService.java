package com.example.oneulnail.domain.reservation.service;

import com.example.oneulnail.domain.reservation.dto.request.ReservationRegisterReqDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationInfoResDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationRegisterResDto;
import com.example.oneulnail.domain.reservation.entity.Reservation;
import com.example.oneulnail.domain.reservation.mapper.ReservationMapper;
import com.example.oneulnail.domain.reservation.repository.ReservationRepository;
import com.example.oneulnail.domain.shop.entity.Shop;
import com.example.oneulnail.domain.shop.service.ShopService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.config.security.oauth2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShopService shopService;
    private final AuthService authService;
    private final ReservationMapper reservationMapper;

    @Transactional
    public ReservationRegisterResDto register(
            HttpServletRequest request,
            ReservationRegisterReqDto reservationRegisterReqDto) {
        String email = authService.extractEmailFromJwt(request);
        User foundUser = authService.findUserByEmail(email);
        Shop foundShop = shopService.findById(reservationRegisterReqDto.getShopId());
        Reservation newReservation = buildReservation(reservationRegisterReqDto, foundUser, foundShop);
        return saveReservation(newReservation, foundUser, foundShop);
    }

    @Transactional(readOnly = true)
    public Slice<ReservationInfoResDto> findReservationsByShopId(Long shopId, Pageable pageable) {
        Slice<Reservation> reservations = reservationRepository.findReservationsByShopIdSlice(shopId, pageable);
        return reservations.map(reservation -> reservationMapper.reservationEntityToReservationInfo(reservation));
    }

    public Slice<ReservationInfoResDto> findAll(Pageable pageable) {
        Slice<Reservation> reservations = reservationRepository.findAll(pageable);
        return reservations.map(reservation -> reservationMapper.reservationEntityToReservationInfo(reservation));
    }

    private Reservation buildReservation(ReservationRegisterReqDto reservationRegisterReqDto, User user, Shop shop){
        return Reservation.builder()
                .date(reservationRegisterReqDto.getDate())
                .user(user)
                .shop(shop)
                .build();
    }

    private ReservationRegisterResDto saveReservation(Reservation reservation, User user, Shop shop){
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.reservationRegisterEntityToDto(savedReservation, user, shop);
    }
}
