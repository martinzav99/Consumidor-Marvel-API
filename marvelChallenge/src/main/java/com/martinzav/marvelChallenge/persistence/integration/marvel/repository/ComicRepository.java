package com.martinzav.marvelChallenge.persistence.integration.marvel.repository;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.martinzav.marvelChallenge.dto.MyPageable;
import com.martinzav.marvelChallenge.persistence.integration.marvel.MarvelAPIConfig;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.ComicDto;

import jakarta.annotation.PostConstruct;

@Repository
public class ComicRepository {

    @Autowired
    private MarvelAPIConfig marvelAPIConfig;

    @Value("${integration.marvel.base-path}")
    private String basePath;
    private String comicPath;

    @PostConstruct
    private void setPath(){
        comicPath = basePath.concat("/").concat("comics");
    }

    public List<ComicDto> findAll(Long characterId, MyPageable pageable) {
        Map<String,String> marvelQueryParams = getQueryParamsForFindAll(characterId,pageable);
        JsonNode response = httpClientService.doGet(comicPath,marvelQueryParams,JsonNode.class);
        return comicMapper.toDtoList(response);
    }

    public ComicDto findById(Long comicId) {
        Map<String,String> marvelQueryParams = marvelAPIConfig.getAuthenticationQueryParams();
        String finalUrl = comicPath.concat("/").concat(Long.toString(comicId));
        JsonNode response = httpClientService.doGet(finalUrl,marvelQueryParams,JsonNode.class);
        return comicMapper.toDtoList(response).get(0);
    }
    
    private Map<String, String> getQueryParamsForFindAll(Long characterId, MyPageable pageable) {
        Map<String, String> marvelQueryParams = marvelAPIConfig.getAuthenticationQueryParams();
        
        marvelQueryParams.put("offset",Long.toString(pageable.offset()));
        marvelQueryParams.put("limit",Long.toString(pageable.limit()));

        if (characterId != null && characterId.longValue() > 0)
            marvelQueryParams.put("comics",Long.toString(characterId));
        
        return marvelQueryParams;
    }
}
