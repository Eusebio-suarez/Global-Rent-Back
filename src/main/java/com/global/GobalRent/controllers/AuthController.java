package com.global.GobalRent.controllers;

import java.util.Optional;

import com.global.GobalRent.services.Mailservice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.global.GobalRent.dto.request.LoginRequestDTO;
import com.global.GobalRent.dto.request.UserRequestDTO;
import com.global.GobalRent.dto.response.LoginResponseDTO;
import com.global.GobalRent.dto.response.UserResponseDTO;
import com.global.GobalRent.entity.UserEntity;
import com.global.GobalRent.exceptions.ExceptionImpl;
import com.global.GobalRent.services.UserService;
import com.global.GobalRent.utils.ApiResponse;
import com.global.GobalRent.utils.JwtUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Mailservice mailservice;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>>loggin(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        
        Optional<UserEntity>userOptional= userService.tryLoggin(loginRequestDTO);

        if(userOptional.isEmpty()){
            throw  new ExceptionImpl("correo o contreseña incorecta.", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userOptional.get();

        String token = jwtUtils.generateToken(user.getEmail());

        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(ApiResponse.<LoginResponseDTO>builder()
                .success(true)
                .message("inicio de sesión correcto")
                .data(LoginResponseDTO.builder()
                    .token(token)
                    .build()
                )
                .build()
            );
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody UserRequestDTO userRequestDTO){

        UserResponseDTO user = userService.registerUser(userRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.<UserResponseDTO>builder()
                .success(true)
                .message("se creado correctamente")
                .data(user)
                .build()
            );
    }
    @GetMapping("/mail")
    public String sendMail(){
        mailservice.senEmail("eusebiosuaresmartines@gmail.com","Prueba","este es un mensage de prueba");
        return "Exito";
    }
}
