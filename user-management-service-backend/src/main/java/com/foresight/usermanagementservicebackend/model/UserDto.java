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
    @Schema( example = "1", required = true)
    private Long id;
    @Schema( example = "Abdelrahman", required = true)
    private String firstname;
    @Schema( example = "Abdelrahman", required = true)
    private String lastname;
    @Schema( example = "ahmed@foresight.com", required = true)
    private String email;
    @Schema( example = " ADMIN", required = true)
    private UserRole role;
    @Schema( example = "true", required = true)
    private boolean enabled;
}
