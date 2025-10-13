package com.global.GobalRent.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarCreatedDTO {

    private String licensePlate;

    private LocalDate createAt;

    private LocalDate updateAt;
}
