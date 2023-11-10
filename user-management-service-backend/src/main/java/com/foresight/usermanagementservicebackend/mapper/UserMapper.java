package com.foresight.usermanagementservicebackend.mapper;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.model.UserDto;
import com.foresight.usermanagementservicebackend.model.UserUpdateRequest;


public class UserMapper {

    public static SystemUser userDtoToSystemUser(UserDto userDto){
        SystemUser user=new SystemUser();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setEnabled(userDto.isEnabled());
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
        userDto.setFirstname(systemUser.getFirstname());
        userDto.setLastname(systemUser.getLastname());
        userDto.setEmail(systemUser.getEmail());
        userDto.setPassword(systemUser.getPassword());
        userDto.setRole(systemUser.getRole());
        userDto.setEnabled(systemUser.isEnabled());
        return userDto;
    }

}
