package com.global.GobalRent.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.global.GobalRent.dto.request.CarRequestDTO;
import com.global.GobalRent.dto.response.CarCreatedDTO;
import com.global.GobalRent.dto.response.CarResponseDTO;
import com.global.GobalRent.entity.CarEntity;
import com.global.GobalRent.exceptions.ExceptionImpl;
import com.global.GobalRent.repository.CarRepository;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository;

    public List<CarResponseDTO> getActiveCars(){
        
        List<CarEntity> carsEntity = carRepository.findByStatusTrue();

        if(carsEntity.isEmpty()){
            throw new ExceptionImpl("No se encontraron carros.",HttpStatus.NOT_FOUND);
        }

        return carsEntity.stream()
            .map(carEntity -> CarResponseDTO.builder()
                .image(carEntity.getImage())
                .model(carEntity.getModel())
                .type(carEntity.getType())
                .people(carEntity.getPeople())
                .bags(carEntity.getBags())
                .price(carEntity.getPrice())
                .build()
            )
            .toList();
    }

    public CarCreatedDTO registerCar(CarRequestDTO carRequestDTO){

        if(carRepository.existsById(carRequestDTO.getLicensePlate())){
            throw new ExceptionImpl("El carro ya existe.",HttpStatus.CONFLICT);
        }

        CarEntity carEntity = CarEntity.builder()
            .licensePlate(carRequestDTO.getLicensePlate())
            .image(carRequestDTO.getImage())
            .model(carRequestDTO.getModel())
            .type(carRequestDTO.getType())
            .people(carRequestDTO.getPeople())
            .bags(carRequestDTO.getBags())
            .price(carRequestDTO.getPrice())
            .status(true)
            .createAt(LocalDate.now())
            .build();

        CarEntity registeredCar = carRepository.save(carEntity);
        
        if(registeredCar==null){
            throw new ExceptionImpl("error al crear el carro",HttpStatus.BAD_REQUEST);
        }

        return CarCreatedDTO.builder()
            .licensePlate(registeredCar.getLicensePlate())
            .createAt(registeredCar.getCreateAt())
            .updateAt(registeredCar.getUpdateAt())
            .build();
    }
}
