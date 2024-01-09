package com.martinzav.marvelChallenge.persistence.integration.marvel.repository;

import java.util.List;

import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto.CharacterInfoDto;

public class CharacterRepository {

    public List<CharacterDto> findAll(String name, int[] comics, int[] series, MyPageable pageable) {
        return null;
    }

    public CharacterInfoDto findById(Long characterId) {
        return null;
    }
    
}
