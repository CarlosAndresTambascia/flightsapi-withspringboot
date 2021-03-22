package com.carlostambascia.controller;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carlostambascia.security.JwtCommon;
import com.carlostambascia.security.model.AuthRequest;
import com.carlostambascia.security.model.AuthResponse;
import com.carlostambascia.service.UserServiceImpl;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthenticationController implements JwtCommon {
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl usrService;

    @PostMapping("/")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        return Try.of(() -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())))
                .filter(Objects::nonNull)
                .map(authenticatedUser -> usrService.loadUserByUsername(authenticatedUser.getName()))
                .map(this::generateToken)
                .map(jwt -> ResponseEntity.ok(new AuthResponse(jwt)))
                .getOrElse(ResponseEntity.badRequest().build());
    }
}