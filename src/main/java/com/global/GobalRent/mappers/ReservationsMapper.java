package com.global.GobalRent.mappers;

import com.global.GobalRent.dto.response.ReservationResponseDTO;
import com.global.GobalRent.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationsMapper {

    @Mapping(source = "car.model", target = "carModel")
    @Mapping(source = "car.image.securedUrl", target = "carImg")
    ReservationResponseDTO toDTO (ReservationEntity reservation);

    List<ReservationResponseDTO> toDTOList(List<ReservationEntity> reservations);
}
