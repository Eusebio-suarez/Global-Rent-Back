package com.global.GobalRent.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
    
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    @Size(min=8)
    private String password;
}
