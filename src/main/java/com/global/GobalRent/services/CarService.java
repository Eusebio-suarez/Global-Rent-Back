package com.global.GobalRent.services;

import java.time.LocalDate;
import java.util.List;

import com.global.GobalRent.dto.response.CarResponseAdminDTO;
import com.global.GobalRent.entity.ImgEntity;
import com.global.GobalRent.mappers.CarsMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.global.GobalRent.dto.request.CarRequestDTO;
import com.global.GobalRent.dto.response.CarCreatedDTO;
import com.global.GobalRent.dto.response.CarResponseDTO;
import com.global.GobalRent.entity.CarEntity;
import com.global.GobalRent.exceptions.ExceptionImpl;
import com.global.GobalRent.repository.CarRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CarService {
    
    private final CarRepository carRepository;

    private final CloudinaryService cloudinary;

    private final CarsMapper carsMapper;

    public List<CarResponseAdminDTO> getAllCars(){

        List<CarEntity> cars = carRepository.findAll();

        return carsMapper.toAdminResponseDTOList(cars);
    }

    @Transactional(readOnly = true)
    public List<CarResponseDTO> getAvailablesCars(LocalDate startDate, LocalDate endDate){

        List<CarEntity> cars = carRepository.findCarsAvailableByDates(startDate,endDate);

        return carsMapper.toResponseDTOList(cars);
    }

    @Transactional(readOnly = true)
    public List<CarResponseDTO> getActiveCars(){
        
        List<CarEntity> carsEntity = carRepository.findByStatusTrue();

        if(carsEntity.isEmpty()){
            throw new ExceptionImpl("No se encontraron carros.",HttpStatus.NOT_FOUND);
        }

        return carsMapper.toResponseDTOList(carsEntity);
    }

    @Transactional
    public CarCreatedDTO registerCar(CarRequestDTO carRequestDTO, MultipartFile image) {

        if(carRepository.existsById(carRequestDTO.getLicensePlate())){
            throw new ExceptionImpl("El carro ya existe.",HttpStatus.CONFLICT);
        }

        ImgEntity img = cloudinary.uploadImg(image);

        CarEntity carEntity = CarEntity.builder()
            .licensePlate(carRequestDTO.getLicensePlate())
            .image(img)
            .model(carRequestDTO.getModel())
            .type(carRequestDTO.getType())
            .people(carRequestDTO.getPeople())
            .bags(carRequestDTO.getBags())
            .price(carRequestDTO.getPrice())
            .status(true)
            .createAt(LocalDate.now())
            .build();

        CarEntity registeredCar = carRepository.save(carEntity);

        return CarCreatedDTO.builder()
            .licensePlate(registeredCar.getLicensePlate())
            .createAt(registeredCar.getCreateAt())
            .updateAt(registeredCar.getUpdateAt())
            .build();
    }
}
