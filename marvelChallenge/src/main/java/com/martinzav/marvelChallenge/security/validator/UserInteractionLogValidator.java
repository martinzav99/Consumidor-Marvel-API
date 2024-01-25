package com.martinzav.marvelChallenge.security.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.martinzav.marvelChallenge.service.AuthenticationService;

@Component("interactionLogValidator")
public class UserInteractionLogValidator {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    public boolean validate(String username){
        String userLoggedIn = authenticationService.getUserLoggedIn().getUsername();
        return userLoggedIn.equals(username);
    }
}
