package com.global.GobalRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.GobalRent.entity.ReservationEntity;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    
    public List<ReservationEntity> findByCar_licensePlateAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String licensePlate, LocalDate EndDate, LocalDate StartDate);
}
