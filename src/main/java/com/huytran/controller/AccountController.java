package com.huytran.controller;

import com.huytran.jwt.JwtTokenProvider;
import com.huytran.payload.AuthenticationRequest;
import com.huytran.payload.AuthenticationResponse;
import com.huytran.security.AccountUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public AuthenticationResponse authenticateAccount(@Valid @RequestBody AuthenticationRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            //SecurityContextHolder.getContext().setAuthentication(authentication);

            final String jwt = tokenProvider.generateToken((AccountUserDetails) authentication.getPrincipal());
            return new AuthenticationResponse(jwt);
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password ",e);
        }
    }
}

