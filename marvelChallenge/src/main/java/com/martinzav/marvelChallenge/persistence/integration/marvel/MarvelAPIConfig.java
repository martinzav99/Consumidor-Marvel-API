package com.martinzav.marvelChallenge.persistence.integration.marvel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MarvelAPIConfig {

    @Autowired
    @Qualifier("md5Encoder")
    private PasswordEncoder md5Encoder;

    private Long timestamp = new Date(System.currentTimeMillis()).getTime();
    
    @Value("${integration.marvel.private-key}")
    private String privateKey;
    
    @Value("${integration.marvel.public-key}")
    private String publicKey;

    private String getHash(){
        String hashUncoded = Long.toString(timestamp).concat(privateKey).concat(publicKey);
        return md5Encoder.encode(hashUncoded);
    }

    public Map<String,String> getAuthenticationQueryParams(){
        Map<String,String> securityQueryParams = new HashMap<>();
        securityQueryParams.put("ts", Long.toString(timestamp));
        securityQueryParams.put("apikey", publicKey);
        securityQueryParams.put("hash", getHash());

        return securityQueryParams;
    }
}
