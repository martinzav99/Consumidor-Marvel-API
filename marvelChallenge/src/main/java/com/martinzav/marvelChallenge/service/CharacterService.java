package com.martinzav.marvelChallenge.service;

import java.util.List;

import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto;

public interface CharacterService {
    
    List<CharacterDto> findAll(String name, int[] comics, int[] series, MyPageable pageable);

    CharacterDto.CharacterInfoDto findInfoById(Long characterId);

}
