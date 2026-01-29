package com.global.GobalRent.services;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.global.GobalRent.dto.response.ReservationAdminResponseDTO;
import com.global.GobalRent.mappers.ReservationsMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final  CarRepository carRepository;

    private final Mailservice mailservice;

    private final ReservationsMapper reservationsMapper;

    public List<ReservationResponseDTO> getReserves(HttpServletRequest request){

        String email = jwtUtils.getSubjectByRequest(request);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ExceptionImpl("no se encontro e usuario", HttpStatus.NOT_FOUND));

        return reservationsMapper.toDTOList(user.getReservations());

    }

    public ReservationResponseDTO reserve(ReservationRequestDTO reservationRequestDTO, HttpServletRequest request )  {

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
                .startPlace(reservationRequestDTO.getStartPlace())
                .endPlace(reservationRequestDTO.getEndPlace())
                .startDate(reservationRequestDTO.getStartDate())
                .startTime(reservationRequestDTO.getStartTime())
                .endDate(reservationRequestDTO.getEndDate())
                .endTime(reservationRequestDTO.getEndTime())
                .build();

        ReservationEntity registeredReservation = reservationRepository.save(reservation);

        mailservice.sendEmail(registeredReservation);

        return reservationsMapper.toDTO(registeredReservation);
    }

    public List<ReservationAdminResponseDTO> getAllReserves(){

        return reservationsMapper.toAdminDTOList(reservationRepository.findAll());
    }

    public ReservationAdminResponseDTO getReserveById(Long id){

        ReservationEntity reserve = reservationRepository.findById(id)
                .orElseThrow(() -> new ExceptionImpl("Not found",HttpStatus.NOT_FOUND));

        return reservationsMapper.toAdminDTO(reserve);
    }
}
