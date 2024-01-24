package com.martinzav.marvelChallenge.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record UserInteractionLogDto(long id, String url, String httpMethod, String username,@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")LocalDateTime timestamp, String remoteAddres) {
    
}
