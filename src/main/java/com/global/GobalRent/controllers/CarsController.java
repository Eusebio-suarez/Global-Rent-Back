package com.global.GobalRent.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.GobalRent.dto.request.CarRequestDTO;
import com.global.GobalRent.dto.response.CarCreatedDTO;
import com.global.GobalRent.dto.response.CarResponseDTO;
import com.global.GobalRent.services.CarService;
import com.global.GobalRent.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {

    @Autowired
    private CarService carService;
    
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CarResponseDTO>>> getCars(){

        List<CarResponseDTO> cars = carService.getActiveCars();

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.<List<CarResponseDTO>>builder()
                .success(true)
                .message("se obtuvieron correctamente los carros")
                .data(cars)
                .build()
            );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CarCreatedDTO>> registerCar(@Valid @RequestBody CarRequestDTO carRequestDTO){
        
        CarCreatedDTO car = carService.registerCar(carRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<CarCreatedDTO>builder()
                .success(true)
                .message("exito")
                .data(car)
                .build()
            );
    }
}