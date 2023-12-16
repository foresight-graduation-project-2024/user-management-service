package com.foresight.usermanagementservicebackend.model;

import com.foresight.usermanagementservicebackend.entity.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Schema(name = "firstname", example = "Abdelrahman", required = true)
    private String email;
    @Schema(name = "user role", example = " ADMIN", required = true)
    private UserRole role;
}
