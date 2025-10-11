package com.global.GobalRent.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.GobalRent.dto.UserResponseDTO;
import com.global.GobalRent.entity.UserEntity;
import com.global.GobalRent.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO>getUsers(){
    
        List<UserEntity> userEntitys = userRepository.findAll();

        if(userEntitys.isEmpty()){

            throw new RuntimeException("no se encontraron usuarios");
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
