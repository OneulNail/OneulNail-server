package com.example.oneulnail.domain.reservation.controller;


import com.example.oneulnail.domain.reservation.dto.request.ReservationRegisterReqDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationInfoResDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationRegisterResDto;
import com.example.oneulnail.domain.reservation.exception.ReservationOverlapException;
import com.example.oneulnail.domain.reservation.service.ReservationService;
import com.example.oneulnail.domain.user.entity.User;
import com.example.oneulnail.global.annotation.LoginUser;
import com.example.oneulnail.global.response.ResultCode;
import com.example.oneulnail.global.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "예약")
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "예약 등록")
    @PostMapping
    public ResponseEntity<ResultResponse> register(
            @Parameter(hidden = true) @LoginUser User user,
            @RequestBody ReservationRegisterReqDto reservationRegisterReqDto) {
        boolean isOverlap = reservationService.isReservationOverlap(reservationRegisterReqDto);
        if (isOverlap) {throw new ReservationOverlapException();}
        ReservationRegisterResDto registerResDto = reservationService.register(user, reservationRegisterReqDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RESERVATION_CREATE_SUCCESS, registerResDto));
    }

    @Operation(summary = "유저의 가게별 예약 조회")
    @GetMapping("/{shopId}")
    public ResponseEntity<ResultResponse> findAllByShopId(
            @PathVariable Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ReservationInfoResDto> reservationInfoResDtoList = reservationService.findReservationsByShopId(shopId, pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_RESERVATION_BY_SHOP_SUCCESS, reservationInfoResDtoList));
    }

    @Operation(summary = "유저의 예약 전체 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ReservationInfoResDto> reservationInfoResDtoList = reservationService.findAll(pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_RESERVATION_SUCCESS, reservationInfoResDtoList));
    }

    @Operation(summary = "가게의 예약 가능한 시간대 조회")
    @GetMapping("/availableTime/{shopId}")
    public ResponseEntity<ResultResponse> getAvailableTimeSlots(
            @PathVariable Long shopId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate){

        Slice<ReservationInfoResDto> availableTime = reservationService.getAvailableTimeSlots(shopId, selectedDate);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_AVAILABLE_TIME_SLOTS_SUCCESS, availableTime));

    }
}
