package com.foresight.usermanagementservicebackend.controller;


import com.foresight.usermanagementservicebackend.model.UserCreateRequest;
import com.foresight.usermanagementservicebackend.model.UserDto;
import com.foresight.usermanagementservicebackend.model.UserUpdateRequest;
import com.foresight.usermanagementservicebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin

public class UserController {
    private final UserService userService;


    @Operation(summary = "register new user", description = "adds new user to the system and returns nothing after adding it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })
    @PostMapping
    public void add(@RequestBody @Valid UserCreateRequest request){
        userService.addUser(request);
    }

    @Operation(summary = "get all users", description = "returns all users no matter they are enabled or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })

    @GetMapping
    public List<UserDto> getAll(){
       return userService.getAllUsers();
    }

    @Operation(summary = "get user by its email", description = "returns the user details for this email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })

    @GetMapping("/{email}")
    public UserDto getUser(@PathVariable("email") String email)
    {
        return userService.getUserByEmail(email);
    }

    @Operation(summary = "update user by its id", description = "replaces this info with user info to be updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })


    @PutMapping("/{id}")
    public void modifyUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request){
        userService.updateUser(id, request);
    }

    @Operation(summary = "activate user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })


    @PutMapping(path = "/{id}/activate")
    public void activate(@PathVariable(name = "id") Long id)
    {
        userService.activate(id);


    }

    @Operation(summary = "deactivate user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })
    @PutMapping(path = "/{id}/deactivate")
    public void deactivate(@PathVariable(name = "id") Long id)
    {
        userService.deactivate(id);


    }
}
