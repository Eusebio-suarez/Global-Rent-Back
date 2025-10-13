package com.global.GobalRent.services;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.global.GobalRent.dto.request.ReservationRequestDTO;
import com.global.GobalRent.dto.response.ReservationResponseDTO;
import com.global.GobalRent.entity.CarEntity;
import com.global.GobalRent.entity.ReservationEntity;
import com.global.GobalRent.entity.UserEntity;
import com.global.GobalRent.exceptions.ExceptionImpl;
import com.global.GobalRent.repository.CarRepository;
import com.global.GobalRent.repository.ReservationRepository;
import com.global.GobalRent.repository.UserRepository;
import com.global.GobalRent.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    public ReservationResponseDTO reserve(ReservationRequestDTO reservationRequestDTO, HttpServletRequest request ){

        String email = jwtUtils.getSubjectByRequest(request);

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){

            throw new ExceptionImpl("No se encontro el usuario",HttpStatus.NOT_FOUND);
        }

        UserEntity user = userOptional.get();


        Optional<CarEntity> carOptional = carRepository.findById(reservationRequestDTO.getLicensePlate());
    
        if(carOptional.isEmpty()){

            throw new ExceptionImpl("no se encontro el carro.",HttpStatus.NOT_FOUND);
        }

        CarEntity car = carOptional.get();


        List<ReservationEntity> reserves = reservationRepository.findByCar_licensePlateAndStartDateLessThanEqualAndEndDateGreaterThanEqual(car.getLicensePlate(),reservationRequestDTO.getEndDate(),reservationRequestDTO.getStartDate());
        
        //verificar que la reserva no se cruze con las ya existentes
        if(!reserves.isEmpty()){
            
            throw new ExceptionImpl("la reserva se cruza con otras.",HttpStatus.CONFLICT);
        }

        Double price = car.getPrice();

        //calcular la cantidad de dias de la reserva
        Long days = ChronoUnit.DAYS.between(reservationRequestDTO.getStartDate(), reservationRequestDTO.getEndDate());

        if(days <= 0){
            throw new ExceptionImpl("las fechas deben detener al menos un dia de diferencia.",HttpStatus.BAD_REQUEST);
        }

        Double totalPrice = price * days;

        
        ReservationEntity reservation = ReservationEntity.builder()
            .totalPrice(totalPrice)
            .user(user)
            .car(car)
            .startDate(reservationRequestDTO.getStartDate())
            .endDate(reservationRequestDTO.getEndDate())
            .build();

        ReservationEntity registeredReservation = reservationRepository.save(reservation);

        return ReservationResponseDTO.builder()
            .startDate(registeredReservation.getStartDate())
            .endDate(registeredReservation.getEndDate())
            .totalPrice(registeredReservation.getTotalPrice())
            .build();
    }
}
