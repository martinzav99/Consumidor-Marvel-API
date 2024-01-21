package com.martinzav.marvelChallenge.service;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.martinzav.marvelChallenge.dto.security.LoginRequest;
import com.martinzav.marvelChallenge.dto.security.LoginResponse;
import com.martinzav.marvelChallenge.persistence.entity.User;

import jakarta.validation.Valid;

public class AuthenticationService {

    @Autowired
    private HttpSecurity httpSecurity;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    public LoginResponse aunthenticate(@Valid LoginRequest loginRequest) {
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.username());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, loginRequest.password(),user.getAuthorities());
        authenticationManager.authenticate(authentication);

        String jwt = jwtService.generateToken(user,generateExtraClaims(user));

        return new LoginResponse(jwt);
    }

    private Map<String,Object> generateExtraClaims(UserDetails user) {
        Map<String,Object> extraClaims = new HashMap<>();

        String roleName = ((User) user).getRole().getName().name();
        extraClaims.put("role", roleName);
        extraClaims.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return extraClaims;
    }

    public void logout() {
        try {
            httpSecurity.logout(logoutConfig -> {
                logoutConfig.deleteCookies("JSESSIONID")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
