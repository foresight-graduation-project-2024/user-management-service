package com.foresight.usermanagementservicebackend.model;

import com.foresight.usermanagementservicebackend.entity.UserRole;
import lombok.Data;

@Data
public class SearchCriteria {
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
    private Boolean enabled;

}
