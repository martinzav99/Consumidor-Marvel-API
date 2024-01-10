package com.martinzav.marvelChallenge.persistence.integration.marvel.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.MarvelAPIConfig;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto.CharacterInfoDto;
import com.martinzav.marvelChallenge.service.HttpClientService;

import jakarta.annotation.PostConstruct;

@Repository
public class CharacterRepository {

    @Autowired
    private MarvelAPIConfig marvelAPIConfig;

    @Autowired
    private HttpClientService httpClientService;

    @Value("${integration.marvel.base-path}")
    private String basePath;
    private String characterPath;

    @PostConstruct
    private void setPath(){
        characterPath = basePath.concat("/").concat("characters");
    }

    public List<CharacterDto> findAll(String name, int[] comics, int[] series, MyPageable pageable) {
        Map<String,String> marvelQueryParams = getQueryParamsForFindAll(name,comics,series,pageable);
        JsonNode response = httpClientService.doGet(characterPath, marvelQueryParams,JsonNode.class);
        return CharacterMapper.toDtoList(response);
    }

    public CharacterDto.CharacterInfoDto findById(Long characterId) {
        Map<String,String> marvelQueryParams = marvelAPIConfig.getAuthenticationQueryParams();
        String finalUrl = characterPath.concat("/").concat(Long.toString(characterId));
        JsonNode response = httpClientService.doGet(finalUrl,marvelQueryParams,JsonNode.class);
        return CharacterMapper.toDtoList(response).get(0); // it will return only one character or will break before this line
    }

    private Map<String,String> getQueryParamsForFindAll(String name, int[] comics, int[] series, MyPageable pageable){
        
        Map<String,String> marvelQueryParams = marvelAPIConfig.getAuthenticationQueryParams();
        
        marvelQueryParams.put("offset",Long.toString(pageable.offset()));
        marvelQueryParams.put("limit",Long.toString(pageable.limit()));

        if (StringUtils.hasText(name))
            marvelQueryParams.put("name",name);
        
        if (comics != null){
            String comicsAsString = joinIntArray(comics);
            marvelQueryParams.put("comics", comicsAsString);
        }
        
        if (series != null){
            String seriesAsString = joinIntArray(series);
            marvelQueryParams.put("series", seriesAsString);
        }

        return marvelQueryParams;
    }

    private String joinIntArray(int[] comics) {
        List<String> stringArray = IntStream.of(comics).boxed().map(i -> i.toString()).collect(Collectors.toList());
        return String.join(",", stringArray) ;
    }
    
}
