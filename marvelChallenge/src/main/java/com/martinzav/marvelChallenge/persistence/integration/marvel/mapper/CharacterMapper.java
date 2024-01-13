package com.martinzav.marvelChallenge.persistence.integration.marvel.mapper;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.ThumbnailDto;
import com.martinzav.marvelChallenge.persistence.integration.marvel.dto.CharacterDto.CharacterInfoDto;

public class CharacterMapper {
    
    public static List<CharacterDto> toDtoList(JsonNode rootNode){
        List<CharacterDto> characters = new ArrayList<>();
        
        getResults(rootNode).elements().forEachRemaining(nodeI -> {
            characters.add(CharacterMapper.toDto(nodeI));
        });

        return characters;
    }

    private static ArrayNode getResults(JsonNode rootNode){

        if(rootNode == null)
            throw new IllegalArgumentException("El nodo json no puede ser null");

        JsonNode dataNode = rootNode.get("data");
        return (ArrayNode) dataNode.get("results");
    }

    private static CharacterDto toDto(JsonNode nodeI){
        
        if(nodeI == null)
            throw new IllegalArgumentException("El nodo json no puede ser null");

        CharacterDto characterDto = new CharacterDto(nodeI.get("id").asLong(),
                                                  nodeI.get("name").asText(),
                                                  nodeI.get("description").asText(),
                                                  nodeI.get("modified").asText(), 
                                                  nodeI.get("resourceUri").asText());
        return characterDto;
    }

    public static List<CharacterDto.CharacterInfoDto> toInfoDtoList(JsonNode rootNode) {
        List<CharacterDto.CharacterInfoDto> charactersInfo = new ArrayList<>();
        
        getResults(rootNode).elements().forEachRemaining(nodeI -> {
            charactersInfo.add(CharacterMapper.toInfoDto(nodeI));
        });

        return charactersInfo;
    }

    private static CharacterInfoDto toInfoDto(JsonNode nodeI) {
        
        if(nodeI == null)
            throw new IllegalArgumentException("El nodo json no puede ser null");

        JsonNode thumbnailNode = nodeI.get("thumbnail");

        ThumbnailDto thumbnailDto = ThumbnailMapper.toDto(thumbnailNode);

        String image = thumbnailDto.path().concat(".").concat(thumbnailDto.extension());

        CharacterInfoDto characterInfoDto = new CharacterInfoDto(image, nodeI.get("description").asText());
        return characterInfoDto;
    }
}
