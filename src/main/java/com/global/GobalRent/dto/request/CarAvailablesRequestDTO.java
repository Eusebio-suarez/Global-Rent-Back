package com.global.GobalRent.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CarAvailablesRequestDTO  {
    
    LocalDate startDate;

    LocalDate endDate;
}
