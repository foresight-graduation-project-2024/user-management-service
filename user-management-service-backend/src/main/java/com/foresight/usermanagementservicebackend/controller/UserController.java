package com.foresight.usermanagementservicebackend.controller;


import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.entity.UserRole;
import com.foresight.usermanagementservicebackend.exception.ErrorCode;
import com.foresight.usermanagementservicebackend.exception.RuntimeErrorCodedException;
import com.foresight.usermanagementservicebackend.model.*;
import com.foresight.usermanagementservicebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
    public void add(@RequestBody @Valid UserCreateRequest request, @RequestHeader("loggedInUserRole") String userRole){
            checkUserRole(UserRole.ADMIN.toString(),userRole);
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
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id)
    {
        return userService.getUserDto(id);
    }

    @Operation(summary = "update user by its id", description = "replaces this info with user info to be updated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })


    @PutMapping("/{id}")
    public void modifyUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request,@RequestHeader("loggedInUserRole") String userRole){
        checkUserRole(UserRole.ADMIN.toString(),userRole);
        userService.updateUser(id, request);
    }

    @Operation(summary = "activate user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })


    @PutMapping(path = "/{id}/activate")
    public void activate(@PathVariable(name = "id") Long id,@RequestHeader("loggedInUserRole") String userRole)
    {
        checkUserRole(UserRole.ADMIN.toString(),userRole);
        userService.activate(id);


    }

    @Operation(summary = "deactivate user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })
    @PutMapping(path = "/{id}/deactivate")
    public void deactivate(@PathVariable(name = "id") Long id,@RequestHeader("loggedInUserRole") String userRole)
    {
        checkUserRole(UserRole.ADMIN.toString(),userRole);
        userService.deactivate(id);


    }
    @Operation(summary = "get all users summary", description = "it takes the page number and the size of data per page as input and returns an overview of users in addition ,information about the page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })
    @GetMapping("/{pageNumber}/{size}")
    public Page<UserSummary> getUsersSummary(@PathVariable("pageNumber")int number,@PathVariable("size")int size){
        return userService.getAllUsersSummary(PageRequest.of(number, size));
    }
    @GetMapping("/search")
    public Page<UserSummary> getUsersByCriteria(Pageable pageable, SearchCriteria searchCriteria){
        return userService.getAllUsersSummaryByCriteria(pageable,searchCriteria);
    }
    @PutMapping("/changePassword/{id}")
    public void changePassword(@PathVariable("id") Long id,@RequestBody ChangePasswordRequest changePasswordRequest,@RequestHeader("loggedInUserRole") String userRole){
        checkUserRole(UserRole.ADMIN.toString(),userRole);
        userService.updatePassword(id, changePasswordRequest.getNewPassword());
    }

    private void checkUserRole(String wantedRole,String userRole){
        if(!(wantedRole.equals(userRole)))
            throw new RuntimeErrorCodedException(ErrorCode.UNAUTHORIZED_USER);
    }
}
