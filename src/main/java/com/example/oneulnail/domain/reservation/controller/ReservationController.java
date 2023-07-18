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

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public BaseResponse<ReservationRegisterResDto> register(@RequestBody ReservationRegisterReqDto reservationRegisterReqDto) {
        ReservationRegisterResDto registerResDto = reservationService.reservate(reservationRegisterReqDto);
        return BaseResponse.onSuccess(registerResDto);
    }

    @GetMapping("/{shopId}")
    public BaseResponse<Slice<ReservationInfoResDto>> findAllByShopId(
            @PathVariable Long shopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<ReservationInfoResDto> reservationInfoResDtos = reservationService.findReservationsByShopId(shopId, pageable);
        return BaseResponse.onSuccess(reservationInfoResDtos);
    }
}
