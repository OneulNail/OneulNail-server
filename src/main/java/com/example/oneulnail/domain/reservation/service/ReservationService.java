package com.example.oneulnail.domain.reservation.service;

import com.example.oneulnail.domain.reservation.dto.request.ReservationRegisterReqDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationInfoResDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationRegisterResDto;
import com.example.oneulnail.domain.reservation.entity.Reservation;
import com.example.oneulnail.domain.reservation.entity.TimeSlot;
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
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Transactional(readOnly = true)
    public List<TimeSlot> getAvailableTimeSlots(Long shopId, LocalDate selectedDate) {
        List<Reservation> reservations = reservationRepository.findByShopIdAndDate(shopId, selectedDate);

        List<TimeSlot> availableTimeSlots = calculateAvailableTimeSlots(reservations, selectedDate);

        return availableTimeSlots;
    }

    private List<TimeSlot> calculateAvailableTimeSlots(List<Reservation> reservations, LocalDate selectedDate) {
        List<TimeSlot> allTimeSlots = new ArrayList<>();
        for (int hour = 9; hour <= 17; hour++) {
            allTimeSlots.add(new TimeSlot(selectedDate, LocalTime.of(hour, 0), LocalTime.of(hour, 30)));
            allTimeSlots.add(new TimeSlot(selectedDate, LocalTime.of(hour, 30), LocalTime.of(hour + 1, 0)));
        }

        for (Reservation reservation : reservations) {
            LocalTime startTime = reservation.getDate().toLocalTime();
            LocalTime endTime = startTime.plusMinutes(30); // 30분 단위로 가정
            allTimeSlots.removeIf(timeSlot ->
                    (timeSlot.getStartTime().equals(startTime) && timeSlot.getEndTime().equals(endTime))
            );
        }

        return allTimeSlots;
    }
}
