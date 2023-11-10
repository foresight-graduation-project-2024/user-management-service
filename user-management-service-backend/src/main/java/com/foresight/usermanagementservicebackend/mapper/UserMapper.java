package com.foresight.usermanagementservicebackend.mapper;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.model.UserCreateRequest;
import com.foresight.usermanagementservicebackend.model.UserDto;
import com.foresight.usermanagementservicebackend.model.UserUpdateRequest;


public class UserMapper {
    public static SystemUser userCreateRequestToUser(UserCreateRequest request)
    {
        SystemUser user=new SystemUser();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setEnabled(request.isEnabled());
        return user;


    }


    public static SystemUser userUpdateRequestToSystemUser(UserUpdateRequest userUpdateRequest){
        SystemUser user=new SystemUser();
        user.setFirstname(userUpdateRequest.getFirstname());
        user.setLastname(userUpdateRequest.getLastname());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(userUpdateRequest.getPassword());
        user.setRole(userUpdateRequest.getRole());
        user.setEnabled(userUpdateRequest.isEnabled());
        return user;
    }
    public static UserDto SystemUserToDto(SystemUser systemUser){
        UserDto userDto=new UserDto();
        userDto.setId(systemUser.getId());
        userDto.setFirstname(systemUser.getFirstname());
        userDto.setLastname(systemUser.getLastname());
        userDto.setEmail(systemUser.getEmail());
        userDto.setRole(systemUser.getRole());
        userDto.setEnabled(systemUser.isEnabled());
        return userDto;
    }

}
