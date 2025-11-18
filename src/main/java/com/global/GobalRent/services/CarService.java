package com.global.GobalRent.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.global.GobalRent.dto.request.CarAvailablesRequestDTO;
import com.global.GobalRent.dto.request.CarRequestDTO;
import com.global.GobalRent.dto.response.CarCreatedDTO;
import com.global.GobalRent.dto.response.CarResponseDTO;
import com.global.GobalRent.entity.CarEntity;
import com.global.GobalRent.exceptions.ExceptionImpl;
import com.global.GobalRent.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {
    
    private final CarRepository carRepository;

    public List<CarResponseDTO> getAvailablesCars(CarAvailablesRequestDTO carAvailablesRequestDTO){

        List<CarEntity> cars = carRepository.findCarsAvailableByDates(carAvailablesRequestDTO.getStartDate(),carAvailablesRequestDTO.getEndDate());
    
        return cars.stream()
            .map(c -> CarResponseDTO.builder()
                .licensePlate(c.getLicensePlate())
                .image(c.getImage())
                .model(c.getModel())
                .type(c.getType())
                .people(c.getPeople())
                .bags(c.getBags())
                .price(c.getPrice())
                .build()
            )
            .toList();
    }

    public List<CarResponseDTO> getActiveCars(){
        
        List<CarEntity> carsEntity = carRepository.findByStatusTrue();

        if(carsEntity.isEmpty()){
            throw new ExceptionImpl("No se encontraron carros.",HttpStatus.NOT_FOUND);
        }

        return carsEntity.stream()
            .map(carEntity -> CarResponseDTO.builder()
                .image(carEntity.getImage())
                .licensePlate(carEntity.getLicensePlate())
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
        
        if(registeredCar.getLicensePlate()==null){
            throw new ExceptionImpl("error al crear el carro",HttpStatus.BAD_REQUEST);
        }

        return CarCreatedDTO.builder()
            .licensePlate(registeredCar.getLicensePlate())
            .createAt(registeredCar.getCreateAt())
            .updateAt(registeredCar.getUpdateAt())
            .build();
    }
}
