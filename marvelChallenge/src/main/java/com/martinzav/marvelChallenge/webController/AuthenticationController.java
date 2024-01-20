package com.martinzav.marvelChallenge.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.martinzav.marvelChallenge.dto.security.LoginRequest;
import com.martinzav.marvelChallenge.dto.security.LoginResponse;
import com.martinzav.marvelChallenge.service.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authentication(@RequestBody @Valid LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.aunthenticate(loginRequest));
    }

    @PostMapping("/logout")
    public void logout(){
        authenticationService.logout();
    }

}
