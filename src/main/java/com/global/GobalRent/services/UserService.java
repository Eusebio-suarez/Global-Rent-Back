package com.global.GobalRent.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.global.GobalRent.dto.request.LoginRequestDTO;
import com.global.GobalRent.dto.request.UserRequestDTO;
import com.global.GobalRent.dto.response.UserResponseDTO;
import com.global.GobalRent.entity.UserEntity;
import com.global.GobalRent.enums.RolEnum;
import com.global.GobalRent.exceptions.ExceptionImpl;
import com.global.GobalRent.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private PasswordEncoder encoder;

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

    public UserResponseDTO registerUser(UserRequestDTO userRequest){

        UserEntity user = UserEntity.builder()
            .name(userRequest.getName())
            .email(userRequest.getEmail())
            .password(encoder.encode(userRequest.getPassword()))
            .rol(RolEnum.USER)
            .build();

        UserEntity userRegister = userRepository.save(user);

        if(userRegister.getId()== null){
            throw new ExceptionImpl("no se pudo crear el usuario",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return UserResponseDTO.builder()
            .email(userRegister.getEmail())
            .name(userRegister.getName())
            .build();
    }

    public Optional<UserEntity>tryLoggin(LoginRequestDTO loginRequestDTO){

        Optional<UserEntity> userOptional = userRepository.findByEmail(loginRequestDTO.getEmail());

        if(userOptional.isPresent()){

            UserEntity user = userOptional.get();

            if(encoder.matches(loginRequestDTO.getPassword(), user.getPassword())){

                return Optional.of(user);

            }
        }

        return Optional.empty();
    }
}
