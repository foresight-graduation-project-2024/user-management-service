package com.foresight.usermanagementservicebackend.controller;


import com.foresight.usermanagementservicebackend.model.UserDto;
import com.foresight.usermanagementservicebackend.model.UserUpdateRequest;
import com.foresight.usermanagementservicebackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @PostMapping
    public void add(@RequestBody @Valid UserDto userDto){
        userService.addUser(userDto);
    }
    @GetMapping
    public List<UserDto> getAll(){
       return userService.getAllUsers();
    }
    @PutMapping("/{id}")
    public void modifyUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request){
        userService.updateUser(id, request);
    }
}
