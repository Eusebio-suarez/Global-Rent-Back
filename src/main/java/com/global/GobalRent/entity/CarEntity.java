package com.global.GobalRent.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name="cars")
public class CarEntity {
    
    @Id
    private String licensePlate;

    private Long peple;

    private Long bags;

    private String model;

    private String type;

    private LocalDate createAt;

    private LocalDate updateAt;
}
