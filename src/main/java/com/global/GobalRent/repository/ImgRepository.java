package com.global.GobalRent.repository;

import com.global.GobalRent.entity.ImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository  extends JpaRepository<ImgEntity,Long> {
}
