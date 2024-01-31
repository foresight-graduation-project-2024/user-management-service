package com.foresight.usermanagementservicebackend.service;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.exception.ErrorCode;
import com.foresight.usermanagementservicebackend.exception.RuntimeErrorCodedException;
import com.foresight.usermanagementservicebackend.mapper.UserMapper;
import com.foresight.usermanagementservicebackend.model.*;
import com.foresight.usermanagementservicebackend.rappitmq.UserEventPublisher;
import com.foresight.usermanagementservicebackend.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher publisher;

    public void addUser(UserCreateRequest request){
       if(userRepo.existsByEmail(request.getEmail()))
           throw new RuntimeErrorCodedException(ErrorCode.EMAIL_ALREADY_EXISTS);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
       SystemUser user =  userRepo.save(UserMapper.userCreateRequestToUser(request));

       publisher.publish(UserMapper.SystemUserToDto(user));

    }
    public List<UserDto> getAllUsers(){
        List<SystemUser> users= userRepo.findAll();
        return users.stream().map(UserMapper::SystemUserToDto).collect(Collectors.toList());
    }
    private SystemUser getUser(Long id)
    {
        SystemUser user = userRepo.findById(id)
                .orElseThrow(()-> new RuntimeErrorCodedException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        return  user;
    }

    public UserDto getUserDto(Long id)
    {
        SystemUser user = getUser(id);


        return  UserMapper.SystemUserToDto(user);
    }
    public void updateUser(Long id, UserUpdateRequest request){
        SystemUser oldUser = this.getUser(id);

            Optional<SystemUser> userOptional=Optional.of(request).map(UserMapper::userUpdateRequestToSystemUser);
            userOptional.get().setId(id);
            userOptional.get().setEmail(oldUser.getEmail());
            userOptional.get().setPassword(oldUser.getPassword());
            userOptional.get().setEnabled(oldUser.isEnabled());
            userRepo.save(userOptional.get());


    }
    public void updatePassword(Long id,String newPassword){
        SystemUser user=this.getUser(id);
      //  if(passwordEncoder.matches(oldPassword, user.getPassword()))
            user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
    public UserDto getUserByEmail(String email)
    {
        SystemUser user = userRepo.findByEmail(email)
                .orElseThrow(()-> new RuntimeErrorCodedException(ErrorCode.USER_NOT_FOUND_EXCEPTION));
        return UserMapper.SystemUserToDto(user);

    }

    public void activate(Long id)
    {
        changeActiveStatus(id,true);
    }


    public void deactivate(Long id)
    {
        changeActiveStatus(id,false);
    }

    private void changeActiveStatus(Long id, boolean status)
    {
        SystemUser admin = this.getUser(id);
        admin.setEnabled(status);
        userRepo.save(admin);

    }
    public Page<UserSummary> getAllUsersSummary(Pageable pageable){
        return userRepo.findAllUserInfo(pageable);
    }

    public Page<UserSummary> getAllUsersSummaryByCriteria(Pageable pageable, SearchCriteria searchCriteria) {
        return userRepo.findUSerInfoByCriteria(pageable,searchCriteria);
    }
}
