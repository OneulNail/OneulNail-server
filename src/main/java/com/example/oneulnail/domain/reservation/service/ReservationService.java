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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShopService shopService;
    private final ReservationMapper reservationMapper;

    @Transactional
    public ReservationRegisterResDto register(User foundUser, ReservationRegisterReqDto reservationRegisterReqDto) {
        Shop foundShop = shopService.findById(reservationRegisterReqDto.getShopId());
        Reservation newReservation = buildReservation(reservationRegisterReqDto, foundUser, foundShop);
        return saveReservation(newReservation, foundUser, foundShop);
    }

    @Transactional
    public boolean isReservationOverlap(ReservationRegisterReqDto reservationRegisterReqDto){
        List<Reservation> overlappingReservations = reservationRepository.findOverlappingReservations(
                reservationRegisterReqDto.getShopId(),
                reservationRegisterReqDto.getDate(),
                reservationRegisterReqDto.getStartTime(),
                reservationRegisterReqDto.getEndTime());
        overlappingReservations.forEach(reservation -> System.out.println(reservation));
        return !overlappingReservations.isEmpty();
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
                .startTime(reservationRegisterReqDto.getStartTime())
                .endTime(reservationRegisterReqDto.getEndTime())
                .user(user)
                .shop(shop)
                .build();
    }

    private ReservationRegisterResDto saveReservation(Reservation reservation, User user, Shop shop){
        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.reservationRegisterEntityToDto(savedReservation, user, shop);
    }

    @Transactional(readOnly = true)
    public Slice<ReservationInfoResDto> getAvailableTimeSlots(Long shopId, LocalDate selectedDate) {
        Slice<Reservation> reservations = reservationRepository.findByShopIdAndDate(shopId, selectedDate);
        return reservations.map(reservation -> reservationMapper.reservationEntityToReservationInfo(reservation));
    }
}
