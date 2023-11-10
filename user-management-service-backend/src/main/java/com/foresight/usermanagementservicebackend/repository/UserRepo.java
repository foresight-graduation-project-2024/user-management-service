package com.foresight.usermanagementservicebackend.repository;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<SystemUser,Long>
{

    Optional<SystemUser>findByEmail(String email);
}
