package com.global.GobalRent.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.global.GobalRent.entity.UserEntity;
import com.global.GobalRent.repository.UserRepository;
import com.global.GobalRent.security.securityUser;

@Service
public class UserDetailsServiceImpl  implements  UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userOptional = userRepository.findByEmail(username);


        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("no se encontro el usuario");
        }

        UserEntity user = userOptional.get();

        return new securityUser(user);
    }
    
}
