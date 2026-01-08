package com.global.GobalRent.controllers;

import com.global.GobalRent.dto.request.CarPatchRequestDTO;
import com.global.GobalRent.dto.request.CarRequestDTO;
import com.global.GobalRent.dto.response.CarCreatedDTO;
import com.global.GobalRent.dto.response.CarResponseAdminDTO;
import com.global.GobalRent.dto.response.CarResponseDTO;
import com.global.GobalRent.services.CarService;
import com.global.GobalRent.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CarService carService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cars")
    public ResponseEntity<ApiResponse<List<CarResponseAdminDTO>>> getAllCars()  {

        List<CarResponseAdminDTO> cars =  carService.getAllCars();

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<List<CarResponseAdminDTO>>builder()
                        .message("Exito")
                        .success(true)
                        .data(cars)
                        .build()
                );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/cars",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CarCreatedDTO>> registerCar(@RequestPart("image")MultipartFile image, @RequestPart("data")CarRequestDTO car)  {

        CarCreatedDTO carCreated =  carService.registerCar(car,image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CarCreatedDTO>builder()
                        .message("Se creo correcatmente")
                        .success(true)
                        .data(carCreated)
                        .build()
                );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(path = "/cars/{licensePlate}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CarResponseAdminDTO>> updateCar(@PathVariable String licensePlate,@RequestPart("data") CarPatchRequestDTO carRequest,@RequestPart(name = "image",required = false) MultipartFile image ){

        CarResponseAdminDTO car = carService.updateCar(licensePlate,carRequest,image);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<CarResponseAdminDTO>builder()
                        .message("updated correctly")
                        .success(true)
                        .data(car)
                        .build()
                );
    }

    @DeleteMapping("/cars/{licensePlate}")
    public ResponseEntity<ApiResponse<String>>deleteCar(@PathVariable String licensePlate){

        carService.deleteCar(licensePlate);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<String>builder()
                        .message("deleted correctly")
                        .success(true)
                        .data(null)
                        .build()
                );
    }


}
