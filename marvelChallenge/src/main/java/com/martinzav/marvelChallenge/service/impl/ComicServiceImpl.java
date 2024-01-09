package com.martinzav.marvelChallenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.ComicDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.repository.ComicRepository;
import com.martinzav.marvelChallenge.service.ComicService;

public class ComicServiceImpl implements ComicService{

    @Autowired
    private ComicRepository comicRepository;

    @Override
    public List<ComicDto> findAll(Long characterId, MyPageable pageable) {
        return comicRepository.findAll(characterId,pageable);
    }

    @Override
    public ComicDto findById(Long comicId) {
        return comicRepository.findById(comicId);
    }
    
}
