package com.foresight.usermanagementservicebackend.controller;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.model.LoginRequest;
import com.foresight.usermanagementservicebackend.model.LoginResponse;
import com.foresight.usermanagementservicebackend.model.UserInfo;
import com.foresight.usermanagementservicebackend.service.JwtService;
import com.foresight.usermanagementservicebackend.service.LoginService;
import com.foresight.usermanagementservicebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {



    private final LoginService loginService;

    @Operation(summary = "authenticate user to login", description = "returns an access token if the user credentials are valid" +
            "and if not returns an  AUTHENTICATION_EXCEPTION(5). " +
            "if the user credentials are valid and account is not enabled it will return  DEACTIVATED_ACCOUNT(6)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "failure check error code in the response for details")
    })
    @PostMapping
    public LoginResponse login(@RequestBody @Valid LoginRequest request){
        return loginService.authenticate(request);
    }

    @Operation(summary = "do not worry about this as front end or mobile developer")
    @PostMapping("/valid")
    public UserInfo validateToken(@RequestParam("token") String token){
       return loginService.validate(token);
    }

}
