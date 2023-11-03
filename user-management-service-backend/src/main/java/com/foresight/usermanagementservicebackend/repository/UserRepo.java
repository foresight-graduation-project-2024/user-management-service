package com.foresight.usermanagementservicebackend.repository;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<SystemUser,Long>
{

}
