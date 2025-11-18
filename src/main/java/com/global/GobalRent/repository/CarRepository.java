package com.global.GobalRent.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.global.GobalRent.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,String> {
    
    public List<CarEntity> findByStatusTrue();

    @Query("SELECT c.price FROM CarEntity c WHERE c.licensePlate = :plate")
    public Double findPriceByLicensePlate(@Param("plate") String licensePlate);

    @Query("SELECT c from CarEntity c WHERE c.licensePlate NOT IN (SELECT r.car.licensePlate FROM ReservationEntity r WHERE r.startDate <= :endDate AND r.endDate >= :startDate)")
    public List<CarEntity> findCarsAvailableByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate );
}
