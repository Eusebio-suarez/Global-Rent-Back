package com.global.GobalRent.mappers;

import com.global.GobalRent.dto.response.CarResponseAdminDTO;
import com.global.GobalRent.dto.response.CarResponseDTO;
import com.global.GobalRent.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarsMapper {

    @Mapping(source = "image.securedUrl",target = "image")
    CarResponseDTO toResponseDTO (CarEntity car);

    List<CarResponseDTO> toResponseDTOList(List<CarEntity> cars);

    @Mapping(source = "image.securedUrl",target = "image")
    CarResponseAdminDTO toAdminResponseDTO (CarEntity car);

    List<CarResponseAdminDTO> toAdminResponseDTOList(List<CarEntity> cars);

}
