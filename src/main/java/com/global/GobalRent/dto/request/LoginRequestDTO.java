package com.global.GobalRent.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {

    @Email
    @NotBlank
    private String email;

    @Size(min=8)
    @NotBlank
    private String password;
}
