package com.foresight.usermanagementservicebackend.model;

import com.foresight.usermanagementservicebackend.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "firstname is required")
    @NotEmpty()
    @Length(max=50)
    @Pattern(regexp = "^[A-Za-z]+$")
    private String firstname;
    @NotBlank(message = "lastname is required")
    @NotEmpty()
    @Length(max=50)
    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastname;
    @Email(message = "invalid form")
    private String email;
    @Length(min=8,max=32)
    @Pattern(regexp ="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",message = "password should have character ,number and symbol")
    private String password;
    @NotNull
    private UserRole role;
}
