package com.foresight.usermanagementservicebackend.service;

import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.model.LoginRequest;
import com.foresight.usermanagementservicebackend.model.LoginResponse;
import com.foresight.usermanagementservicebackend.model.UserInfo;
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
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse authenticate(LoginRequest request){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authenticate.isAuthenticated() ) {
            Optional<SystemUser> user=userService.getUser(request.getEmail());
            if(user.get().isEnabled())
                return new LoginResponse(jwtService.generateToken(user.get()));
            else
                throw new RuntimeException("not enabled");
        } else {
            throw new RuntimeException("invalid authenticated");
        }
    }
    public UserInfo validate(String token){
        String email = jwtService.extractEmail(token);
        String role = jwtService.extractRole(token);
        return new UserInfo(email,role);
    }

}
