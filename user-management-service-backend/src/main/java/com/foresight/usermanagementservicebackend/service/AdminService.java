package com.foresight.usermanagementservicebackend.service;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.mapper.UserMapper;
import com.foresight.usermanagementservicebackend.model.UserDto;
import com.foresight.usermanagementservicebackend.model.UserUpdateRequest;
import com.foresight.usermanagementservicebackend.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void addUser(UserDto request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Optional<SystemUser> user = Optional.of(request).map(UserMapper::userDtoToSystemUser);
        userRepo.save(user.get());
    }
    public List<UserDto> getAllUsers(){
        List<SystemUser> users= userRepo.findAll();
        return users.stream().map(UserMapper::SystemUserToDto).collect(Collectors.toList());
    }
    public Optional<SystemUser> getUser(Long id){
        return  userRepo.findById(id);
    }
    public void updateUser(Long id, UserUpdateRequest request){
        Optional<SystemUser> userOptional = this.getUser(id);
        if(userOptional.isPresent()) {
            SystemUser user=userOptional.get();
            updateFieldIfNotNull(user::setFirstname, request.getFirstname());
            updateFieldIfNotNull(user::setLastname, request.getLastname());
            updateFieldIfNotNull(user::setPassword, passwordEncoder.encode(request.getPassword()));
            updateFieldIfNotNull(user::setEmail, request.getEmail());
            updateFieldIfNotNull(user::setRole, request.getRole());
            userRepo.save(user);
        }

    }
    private <T> void updateFieldIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
