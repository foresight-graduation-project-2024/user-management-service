package com.foresight.usermanagementservicebackend.mapper;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.model.UserDto;


public class UserMapper {

    public static SystemUser userDtoToSystemUser(UserDto userDto){
        SystemUser user=new SystemUser();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }
    public static UserDto SystemUserToDto(SystemUser systemUser){
        UserDto userDto=new UserDto();
        userDto.setFirstname(systemUser.getFirstname());
        userDto.setLastname(systemUser.getLastname());
        userDto.setEmail(systemUser.getEmail());
        userDto.setPassword(systemUser.getPassword());
        userDto.setRole(systemUser.getRole());
        return userDto;
    }

}
