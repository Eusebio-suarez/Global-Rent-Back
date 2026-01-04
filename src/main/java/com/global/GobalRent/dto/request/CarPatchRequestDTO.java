package com.global.GobalRent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPatchRequestDTO {

    private JsonNullable<String> licensePlate;

    private JsonNullable<String> model;

    private JsonNullable<String> type;

    private JsonNullable<Long> people;

    private JsonNullable<Long> bags;

    private JsonNullable<Double> price;

    private JsonNullable<Boolean> status;

}
