package com.foresight.usermanagementservicebackend.security;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemUserDetailsService implements UserDetailsService {
    @Autowired
    private  UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<SystemUser> systemUser= userRepo.findByEmail(email);
        return systemUser.map(SystemUserDetails::new).orElseThrow(()->new UsernameNotFoundException("invalid user"));
    }
}
