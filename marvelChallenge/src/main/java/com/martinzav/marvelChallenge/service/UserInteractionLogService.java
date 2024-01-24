package com.martinzav.marvelChallenge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.martinzav.marvelChallenge.dto.UserInteractionLogDto;

public interface UserInteractionLogService {

    Page<UserInteractionLogDto> findAll(Pageable pageable);

    Page<UserInteractionLogDto> findByUsername(Pageable pageable, String username);
    
    
}
