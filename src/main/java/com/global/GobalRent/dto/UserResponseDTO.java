package com.global.GobalRent.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    
    private String name;

    private String email;
}
