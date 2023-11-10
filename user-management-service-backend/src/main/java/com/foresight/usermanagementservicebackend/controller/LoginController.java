package com.foresight.usermanagementservicebackend.controller;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.model.LoginRequest;
import com.foresight.usermanagementservicebackend.model.LoginResponse;
import com.foresight.usermanagementservicebackend.model.UserInfo;
import com.foresight.usermanagementservicebackend.service.JwtService;
import com.foresight.usermanagementservicebackend.service.LoginService;
import com.foresight.usermanagementservicebackend.service.UserService;
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
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public LoginResponse login(@RequestBody @Valid LoginRequest request){
        return loginService.authenticate(request);
    }
    @PostMapping("/valid")
    public UserInfo validateToken(@RequestParam("token") String token){
       return loginService.validate(token);
    }

}
