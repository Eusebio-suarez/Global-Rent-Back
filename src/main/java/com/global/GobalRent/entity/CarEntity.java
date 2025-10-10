package com.global.GobalRent.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    private Long people;

    private Long bags;

    private String model;

    private String type;

    @Column(updatable=false)

    @Builder.Default
    private LocalDate createAt = LocalDate.now();

    @Column(updatable=true)
    private LocalDate updateAt;
}
