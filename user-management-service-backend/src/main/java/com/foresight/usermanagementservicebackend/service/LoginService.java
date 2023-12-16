package com.foresight.usermanagementservicebackend.service;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.entity.UserRole;
import com.foresight.usermanagementservicebackend.exception.ErrorCode;
import com.foresight.usermanagementservicebackend.exception.RuntimeErrorCodedException;
import com.foresight.usermanagementservicebackend.model.LoginRequest;
import com.foresight.usermanagementservicebackend.model.LoginResponse;
import com.foresight.usermanagementservicebackend.model.UserInfo;
import com.foresight.usermanagementservicebackend.repository.UserRepo;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarException;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SystemUser user = (SystemUser) authentication.getPrincipal();
        return new LoginResponse(jwtService.generateToken(user));


    }
    public UserInfo validate(String token){
        String email = jwtService.extractEmail(token);
        UserRole role = Enum.valueOf(UserRole.class,jwtService.extractRole(token));
        return new UserInfo(email,role);
    }

}
