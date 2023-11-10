package com.foresight.usermanagementservicebackend.security;

import com.foresight.usermanagementservicebackend.exception.ErrorCode;
import com.foresight.usermanagementservicebackend.exception.RuntimeErrorCodedException;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@AllArgsConstructor
public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {
    UserDetailsService userDetailsService;
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String email = (String)authentication.getPrincipal();
        SystemUserDetails userDetails;
        try {
             userDetails = (SystemUserDetails) userDetailsService.loadUserByUsername(email);
        }
        catch (UsernameNotFoundException ex)
        {
            throw new RuntimeErrorCodedException(ErrorCode.AUTHENTICATION_EXCEPTION);
        }


       if(!passwordEncoder.matches((String)authentication.getCredentials(), userDetails.getPassword()))
           throw new RuntimeErrorCodedException(ErrorCode.AUTHENTICATION_EXCEPTION);

       if(!userDetails.isEnabled())
           throw new RuntimeErrorCodedException(ErrorCode.DEACTIVATED_ACCOUNT);


       Authentication authenticationResult = new UsernamePasswordAuthenticationToken(userDetails.getUser(),null,null);

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
