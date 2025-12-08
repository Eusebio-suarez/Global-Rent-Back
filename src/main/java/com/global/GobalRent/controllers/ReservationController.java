package com.global.GobalRent.controllers;

import java.util.List;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.GobalRent.dto.request.ReservationRequestDTO;
import com.global.GobalRent.dto.response.ReservationResponseDTO;
import com.global.GobalRent.services.ReservationService;
import com.global.GobalRent.utils.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {
    
    private final ReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ReservationResponseDTO>>>getReservations(HttpServletRequest request){

        List<ReservationResponseDTO> reservations = reservationService.getReserves(request);

        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.<List<ReservationResponseDTO>>builder()
                .success(true)
                .message("Exito")
                .data(reservations)
                .build()
            );

    }

    @PostMapping("/reserve")
    public ResponseEntity<ApiResponse<ReservationResponseDTO>>reserve(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO, HttpServletRequest request)  {

        ReservationResponseDTO reservation = reservationService.reserve(reservationRequestDTO, request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<ReservationResponseDTO>builder()
                .success(true)
                .message("Reserva Exitosa.")
                .data(reservation)
                .build()
            );

    }
}
