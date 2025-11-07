package com.global.GobalRent.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarResponseDTO {

    private String licensePlate;
    
    private String image;

    private String model;

    private String type;

    private Long people;

    private Long bags;

    private Double price;
}
