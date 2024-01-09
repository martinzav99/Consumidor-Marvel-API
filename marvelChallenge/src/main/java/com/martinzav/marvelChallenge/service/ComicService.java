package com.martinzav.marvelChallenge.service;

import java.util.List;
import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.ComicDto;

public interface ComicService {

    List<ComicDto> findAll(Long characterId, MyPageable pageable);

    ComicDto findById(Long comicId);
    
}
