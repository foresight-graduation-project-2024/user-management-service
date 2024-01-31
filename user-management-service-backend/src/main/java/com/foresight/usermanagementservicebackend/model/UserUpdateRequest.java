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
    @Schema( example = "Abdelrahman", required = true)
    private String firstname;
    @NotBlank(message = "lastname is required")
    @NotEmpty()
    @Length(max=50)
    @Pattern(regexp = "^[A-Za-z]+$")
    @Schema( example = "Abdelrahman", required = true)
    private String lastname;
    @NotNull
    @Schema( example = " ADMIN", required = true)
    private UserRole role;
}
