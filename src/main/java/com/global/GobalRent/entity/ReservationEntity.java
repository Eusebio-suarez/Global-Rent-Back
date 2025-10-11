package com.global.GobalRent.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="reservations")
public class ReservationEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Long totalPrice;

    //relacion con la entidad user
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private UserEntity user;


    @ManyToOne
    @JoinColumn(name="car_id", nullable = false)
    private CarEntity car;

    private LocalDate startDate;

    private LocalDate endDate;

}
