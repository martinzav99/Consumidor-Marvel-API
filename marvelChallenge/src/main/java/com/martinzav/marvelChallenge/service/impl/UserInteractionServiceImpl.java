package com.martinzav.marvelChallenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.martinzav.marvelChallenge.dto.UserInteractionLogDto;
import com.martinzav.marvelChallenge.mapper.UserInteractionLogMapper;
import com.martinzav.marvelChallenge.persistence.repository.UserInteractionLogRepository;
import com.martinzav.marvelChallenge.service.UserInteractionLogService;

@Service
public class UserInteractionServiceImpl implements UserInteractionLogService{

    @Autowired
    UserInteractionLogRepository userInteractionLogRepository;
    
    @Override
    public Page<UserInteractionLogDto> findAll(Pageable pageable) {
        return userInteractionLogRepository.findAll(pageable).map(UserInteractionLogMapper::toDto);
    }

    @Override
    public Page<UserInteractionLogDto> findByUsername(Pageable pageable, String username) {
        return userInteractionLogRepository.findByUsername(pageable,username).map(UserInteractionLogMapper::toDto);
    }
    
}
