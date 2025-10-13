package com.global.GobalRent.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cars")
public class CarEntity {
    
    @Id
    private String licensePlate;

    private String image;

    private String model;

    private String type;
    
    private Long people;
    
    private Long bags;

    private Double price;

    private boolean status;

    @OneToMany(mappedBy = "car")
    private List<ReservationEntity> reservations;

    @Builder.Default
    @Column(updatable=false)
    private LocalDate createAt = LocalDate.now();

    @Column(updatable=true)
    private LocalDate updateAt;
}
