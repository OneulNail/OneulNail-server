package com.example.oneulnail.domain.reservation.controller;


import com.example.oneulnail.domain.reservation.dto.request.ReservationRegisterReqDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationInfoResDto;
import com.example.oneulnail.domain.reservation.dto.response.ReservationRegisterResDto;
import com.example.oneulnail.domain.reservation.service.ReservationService;
import com.example.oneulnail.global.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public BaseResponse<ReservationRegisterResDto> register(
            HttpServletRequest request,
            @RequestBody ReservationRegisterReqDto reservationRegisterReqDto) {
        ReservationRegisterResDto registerResDto = reservationService.register(request, reservationRegisterReqDto);
        return BaseResponse.onSuccess(registerResDto);
    }

    @GetMapping("/{shopId}")
    public BaseResponse<Slice<ReservationInfoResDto>> findAllByShopId(
            @PathVariable Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ReservationInfoResDto> reservationInfoResDtoList = reservationService.findReservationsByShopId(shopId, pageable);
        return BaseResponse.onSuccess(reservationInfoResDtoList);
    }

    @GetMapping
    public BaseResponse<Slice<ReservationInfoResDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ReservationInfoResDto> reservationInfoResDtoList = reservationService.findAll(pageable);
        return BaseResponse.onSuccess(reservationInfoResDtoList);
    }
}
