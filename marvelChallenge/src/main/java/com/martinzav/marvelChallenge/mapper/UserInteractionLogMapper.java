package com.martinzav.marvelChallenge.mapper;

import com.martinzav.marvelChallenge.dto.UserInteractionLogDto;
import com.martinzav.marvelChallenge.persistence.entity.UserInteractionLog;

public class UserInteractionLogMapper {
    
    public static UserInteractionLogDto toDto(UserInteractionLog entity){
        if(entity == null) 
            return null;

        return new UserInteractionLogDto(entity.getId(), entity.getUrl(),entity.getHttpMethod(), entity.getUsername(),entity.getTimestamp(), entity.getRemoteAdress());
    }
}
