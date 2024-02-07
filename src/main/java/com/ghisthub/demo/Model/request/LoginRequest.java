package com.ghisthub.demo.Model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "please provide a valid email") @NotBlank(message ="user email is required!")
    private String email;

    @NotBlank @Size(min = 8, message = "password must have at least 8 character!")
    private String password;
}
