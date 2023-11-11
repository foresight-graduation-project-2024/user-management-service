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
public class UserDto {
    @Schema(name = "user id", example = "1", required = true)
    private Long id;
    @Schema(name = "firstname", example = "Abdelrahman", required = true)
    private String firstname;
    @Schema(name = "lastname", example = "Abdelrahman", required = true)
    private String lastname;
    @Schema(name = "email", example = "ahmed@foresight.com", required = true)
    private String email;
    @Schema(name = "user role", example = " ADMIN", required = true)
    private UserRole role;
    @Schema(name = "enabled status", example = "true", required = true)
    private boolean enabled;
}
