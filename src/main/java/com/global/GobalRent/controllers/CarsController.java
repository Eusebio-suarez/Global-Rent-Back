package com.global.GobalRent.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.global.GobalRent.dto.request.CarAvailablesRequestDTO;
import com.global.GobalRent.dto.request.CarRequestDTO;
import com.global.GobalRent.dto.response.CarCreatedDTO;
import com.global.GobalRent.dto.response.CarResponseDTO;
import com.global.GobalRent.services.CarService;
import com.global.GobalRent.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarsController {

    private final CarService carService;
    
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

    @GetMapping("/avaliables")
    public ResponseEntity<ApiResponse<List<CarResponseDTO>>> getAvaliables(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate ){

        List<CarResponseDTO> cars = carService.getAvailablesCars(startDate,endDate);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.<List<CarResponseDTO>>builder()
                .success(true)
                .message("Exito al obtener los carros")
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