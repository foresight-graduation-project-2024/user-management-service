package com.foresight.usermanagementservicebackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(message = "invalid form")
    @Schema(name = "email", example = "ahmed@foresight.com", required = true)
    private String email;
    @Schema(name = "password", example = "Aa@123456789", required = true,minLength = 8,maxLength = 32)
    private String password;
}
