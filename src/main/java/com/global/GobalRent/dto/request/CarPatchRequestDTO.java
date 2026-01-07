package com.global.GobalRent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPatchRequestDTO {

    private JsonNullable<String> licensePlate = JsonNullable.undefined();

    private JsonNullable<String> model = JsonNullable.undefined();

    private JsonNullable<String> type = JsonNullable.undefined();

    private JsonNullable<Long> people = JsonNullable.undefined();

    private JsonNullable<Long> bags = JsonNullable.undefined();

    private JsonNullable<Double> price = JsonNullable.undefined();

    private JsonNullable<Boolean> status = JsonNullable.undefined();

}
