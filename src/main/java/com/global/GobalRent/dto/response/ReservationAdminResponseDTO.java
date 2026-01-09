package com.global.GobalRent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAdminResponseDTO {

    private String clientName;

    private String clientEmail;

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
