package com.global.GobalRent.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationRequestDTO {
    
    @NotBlank
    private String licensePlate;

    @NotBlank
    private String startPlace;

    @NotBlank
    private String endPlace;

    @Future
    private LocalDate startDate;

    @NotNull
    private LocalTime startTime;

    @Future
    private LocalDate endDate;

    @NotNull
    private LocalTime endTime;

}
