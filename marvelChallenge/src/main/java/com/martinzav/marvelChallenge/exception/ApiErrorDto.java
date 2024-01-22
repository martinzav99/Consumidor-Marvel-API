package com.martinzav.marvelChallenge.exception;

public record ApiErrorDto(String message, String backendMessage, String method, String url) {
    
}
