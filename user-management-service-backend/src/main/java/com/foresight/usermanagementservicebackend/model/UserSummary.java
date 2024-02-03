package com.foresight.usermanagementservicebackend.model;


import com.foresight.usermanagementservicebackend.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
    private boolean enabled;
}
