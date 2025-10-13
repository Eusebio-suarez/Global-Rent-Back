package com.global.GobalRent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.GobalRent.entity.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,String> {
    
    public List<CarEntity> findByStatusTrue();
}
