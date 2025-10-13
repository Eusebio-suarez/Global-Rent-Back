package com.global.GobalRent.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationRequestDTO {
    
    @NotBlank
    private String licensePlate;

    @Future
    private LocalDate startDate;

    @Future
    private LocalDate endDate;
}
