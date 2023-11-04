package com.foresight.usermanagementservicebackend.controller;

import com.foresight.usermanagementservicebackend.model.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    public void login(@RequestBody @Valid LoginRequest request){
        //TODO: adding the authentication and get the token
    }
}
