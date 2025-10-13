package com.global.GobalRent.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationResponseDTO {
    
    private LocalDate startDate;

    private LocalDate endDate;

    private Double totalPrice;

}
