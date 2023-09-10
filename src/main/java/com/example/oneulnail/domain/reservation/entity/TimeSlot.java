package com.example.oneulnail.domain.reservation.entity;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class TimeSlot {
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
