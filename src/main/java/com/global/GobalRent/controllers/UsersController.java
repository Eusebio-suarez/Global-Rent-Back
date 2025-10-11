package com.global.GobalRent.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.GobalRent.dto.UserResponseDTO;
import com.global.GobalRent.services.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<UserResponseDTO> getUsers(){
        return userService.getUsers();
    }
}
