package com.martinzav.marvelChallenge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto.CharacterInfoDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.repository.CharacterRepository;
import com.martinzav.marvelChallenge.service.CharacterService;

@Service
public class CharacterServiceImpl implements CharacterService{

    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public List<CharacterDto> findAll(String name, int[] comics, int[] series, MyPageable pageable) {
        return characterRepository.findAll(name,comics,series,pageable);
    }

    @Override
    public CharacterInfoDto findInfoById(Long characterId) {
        return characterRepository.findById(characterId);
    }
    
}
