package com.global.GobalRent.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationResponseDTO {

    private String carModel;

    private String carImg;

    private String startPlace;

    private String endPlace;
    
    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDate endDate;

    private LocalTime endTime;

    private Double totalPrice;

}
