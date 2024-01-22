package com.martinzav.marvelChallenge.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handleGeneralException(Exception exception, HttpServletRequest request,WebRequest webRequest){

        if (exception instanceof HttpClientErrorException)
            return handleHttpClientErrorException((HttpClientErrorException)exception,request,webRequest);
        else if(exception instanceof AccessDeniedException)
                return handleAccesDeniedException((AccessDeniedException)exception,request,webRequest);
        else if(exception instanceof AuthenticationCredentialsNotFoundException)
                return handleAuthenticationCredentialsNotFoundException((AuthenticationCredentialsNotFoundException)exception,request,webRequest);
        else
            return handleGenericException(exception,request,webRequest);
    }

    private ResponseEntity<ApiErrorDto> handleHttpClientErrorException(HttpClientErrorException exception, HttpServletRequest request, WebRequest webRequest) {
        ApiErrorDto dto = new ApiErrorDto("Error inesperado al realizar consulta",
                                         exception.getMessage(),
                                         request.getMethod(),
                                         request.getRequestURL().toString());

        return ResponseEntity.status(exception.getStatusCode()).body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleAccesDeniedException(AccessDeniedException exception, HttpServletRequest request,WebRequest webRequest) {
        
        ApiErrorDto dto = new ApiErrorDto("No tienes acceso a este recurso",
                                         exception.getMessage(),
                                         request.getMethod(),
                                         request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException exception, HttpServletRequest request, WebRequest webRequest) {
        ApiErrorDto dto = new ApiErrorDto("No tienes acceso a este recurso",
                                         exception.getMessage(),
                                         request.getMethod(),
                                         request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(dto);
    }

    private ResponseEntity<ApiErrorDto> handleGenericException(Exception exception, HttpServletRequest request, WebRequest webRequest) {
        ApiErrorDto dto = new ApiErrorDto("Error inesperado, por favor realizar un comportamiento correcto",
                                         exception.getMessage(),
                                         request.getMethod(),
                                         request.getRequestURL().toString());

        return ResponseEntity.internalServerError().body(dto);
    }
}
