package com.foresight.usermanagementservicebackend.controller;


import com.foresight.usermanagementservicebackend.model.UserCreateRequest;
import com.foresight.usermanagementservicebackend.model.UserDto;
import com.foresight.usermanagementservicebackend.model.UserUpdateRequest;
import com.foresight.usermanagementservicebackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public void add(@RequestBody @Valid UserCreateRequest request){
        userService.addUser(request);
    }
    @GetMapping
    public List<UserDto> getAll(){
       return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public UserDto getUser(@PathVariable("email") String email)
    {
        return userService.getUserByEmail(email);
    }

    @PutMapping()
    public void modifyUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request){
        userService.updateUser(id, request);
    }

    @PutMapping(path = "/{id}/activate")
    public void activate(@PathVariable(name = "id") Long id)
    {
        userService.activate(id);


    }
    @PutMapping(path = "/{id}/deactivate")
    public void deactivate(@PathVariable(name = "id") Long id)
    {
        userService.deactivate(id);


    }
}
