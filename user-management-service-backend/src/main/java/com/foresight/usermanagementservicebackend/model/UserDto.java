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
    @NotBlank(message = "email is required")
    @NotEmpty()
    @Email(message = "invalid form")
    private String email;
    @Length(min=8,max=50)
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d\\s]).+$\n",message = "password should have at least character, number and symbol")
    private String password;
    @NotNull
    private UserRole role;
}
