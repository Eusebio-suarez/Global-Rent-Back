package com.global.GobalRent.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.global.GobalRent.dto.UserResponseDTO;
import com.global.GobalRent.entity.UserEntity;
import com.global.GobalRent.exceptions.ExceptionImpl;
import com.global.GobalRent.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO>getUsers(){
    
        List<UserEntity> userEntitys = userRepository.findAll();

        if(userEntitys.isEmpty()){

            throw new ExceptionImpl("no se encontraron usuarios.",HttpStatus.NOT_FOUND);
        }

        return userEntitys.stream()
            .map(userEntity -> UserResponseDTO.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .build()
                )
                .toList();
    }
}
