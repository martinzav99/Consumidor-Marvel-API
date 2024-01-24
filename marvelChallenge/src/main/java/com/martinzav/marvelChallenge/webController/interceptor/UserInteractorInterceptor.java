package com.martinzav.marvelChallenge.webController.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.martinzav.marvelChallenge.exception.ApiErrorException;
import com.martinzav.marvelChallenge.persistence.entity.UserInteractionLog;
import com.martinzav.marvelChallenge.persistence.repository.UserInteractionLogRepository;
import com.martinzav.marvelChallenge.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

@Component
public class UserInteractorInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInteractionLogRepository userInteractionLogRepository;

    @Autowired
    @Lazy
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String requestUri = request.getRequestURI();
        
        if(requestUri.startsWith("/comics") || requestUri.startsWith("/characters")){

            UserInteractionLog userInteractionLog = new UserInteractionLog();
            userInteractionLog.setHttpMethod(request.getMethod());
            userInteractionLog.setUrl(request.getRequestURL().toString());

            UserDetails user = authenticationService.getUserLoggedIn();

            userInteractionLog.setUsername(user.getUsername());
            userInteractionLog.setRemoteAdress(request.getRemoteAddr());
            userInteractionLog.setTimestamp(LocalDateTime.now());
            
            try {
                userInteractionLogRepository.save(userInteractionLog);
                return true;
            } catch (Exception e) {
                throw new ApiErrorException("No se lorgo guardar el registro de interacción correctamente");
            }
        }

        return true;
    }

    
}
