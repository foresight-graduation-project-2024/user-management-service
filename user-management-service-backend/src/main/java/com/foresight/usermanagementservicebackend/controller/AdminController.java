package com.foresight.usermanagementservicebackend.controller;


import com.foresight.usermanagementservicebackend.model.UserDto;
import com.foresight.usermanagementservicebackend.model.UserUpdateRequest;
import com.foresight.usermanagementservicebackend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @PostMapping
    public void add(@RequestBody @Valid UserDto userDto){
        adminService.addUser(userDto);
    }
    @GetMapping
    public List<UserDto> getAll(){
       return adminService.getAllUsers();
    }
    @PutMapping("/modify/{id}")
    public void modifyUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request){
        adminService.updateUser(id, request);
    }
}
