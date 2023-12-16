package com.foresight.usermanagementservicebackend.repository;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.model.SearchCriteria;
import com.foresight.usermanagementservicebackend.model.UserInfo;
import com.foresight.usermanagementservicebackend.model.UserSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<SystemUser,Long>
{
    Optional<SystemUser>findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query(value = "select new com.foresight.usermanagementservicebackend.model.UserSummary(u.id,u.firstname,u.lastname) from SystemUser u order by u.id")
    Page<UserSummary> findAllUserInfo(Pageable pageable);
    @Query("select new com.foresight.usermanagementservicebackend.model.UserSummary(u.id,u.firstname,u.lastname) from SystemUser u where u.firstname=COALESCE(:#{#sc.firstname},u.firstname) and u.lastname=COALESCE(:#{#sc.lastname},u.lastname) and u.email=COALESCE(:#{#sc.email},u.email)  and u.role=COALESCE(:#{#sc.role},u.role) and  u.enabled=COALESCE(:#{#sc.enabled},u.enabled)")
    Page<UserSummary> findUSerInfoByCriteria(Pageable pageable, @Param("sc") SearchCriteria searchCriteria);
}
