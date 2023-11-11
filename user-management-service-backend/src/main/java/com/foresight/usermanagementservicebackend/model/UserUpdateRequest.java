package com.foresight.usermanagementservicebackend.model;

import com.foresight.usermanagementservicebackend.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "firstname is required")
    @NotEmpty()
    @Length(max=50)
    @Pattern(regexp = "^[A-Za-z]+$")
    @Schema(name = "firstname", example = "Abdelrahman", required = true)
    private String firstname;
    @NotBlank(message = "lastname is required")
    @NotEmpty()
    @Length(max=50)
    @Pattern(regexp = "^[A-Za-z]+$")
    @Schema(name = "lastname", example = "Abdelrahman", required = true)
    private String lastname;
    @Length(min=8,max=32)
    @Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",message = "password should have character ,number and symbol")
    @Schema(name = "password", example = "Aa@123456789", required = true,minLength = 8,maxLength = 32)
    private String password;
    @NotNull
    @Schema(name = "user role", example = " ADMIN", required = true)
    private UserRole role;
    @NotNull
    @Schema(name = "enabled status", example = "true", required = true)
    private boolean enabled;
}
