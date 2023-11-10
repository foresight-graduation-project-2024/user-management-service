package com.foresight.usermanagementservicebackend.security;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SystemUserDetails implements UserDetails {
    private final SystemUser user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->user.getRole().toString());
    }

    @Override
    public String getPassword() {
        return user.getPassword() ;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
    public SystemUser getUser(){return user;}
}
