package com.solution.ntq.service.impl;


import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.service.base.GoogleService;
import com.solution.ntq.service.base.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nam_Phuong
 * Delear token service
 * Date update 24/7/2019
 */
@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private static final Map<String, Token> tokenList = new HashMap<>();
    private static final int MINUTE_TIMEOUT = 1;
    private static HttpTransport httpTransport = new NetHttpTransport();
    private static JsonFactory jacksonFactory = new JacksonFactory();

    private GoogleService googleService;

    /**
     * @param userId userid
     * @return true if not time out
     */
    @Override
    public boolean isTimeOut(String userId) {
        Token token = getTokenByUserId(userId);
        Date currentTime = new Date();// get current time
        long diff = currentTime.getTime() - token.getTime().getTime();
        long diffMinutes = diff / (60 * 1000) % 60;// caculate diffirent minute between two time
        return (diffMinutes >= MINUTE_TIMEOUT);
    }

    /**
     * add token to map
     *
     * @param userId
     * @param idToken
     */
    @Override
    public void addToken(String userId, String idToken) {
        try {
            clearIdToken(idToken);
            Token tokenNew = new Token(new Date(), idToken);
            tokenList.put(userId, tokenNew);
        } catch (NullPointerException ex) {
            clearIdToken(idToken);
        }
    }

    /**
     * Clear all token have in list
     */
    @Override
    public void clearIdToken(String idToken) {
        tokenList.remove(idToken);
    }

    /**
     * get token by user
     *
     * @param userId
     * @return
     */
    @Override
    public Token getTokenByUserId(String userId) {
        return tokenList.get(userId);
    }

    /**
     * Check token isEmpty
     */
    @Override
    public boolean isEmpty() {
        return tokenList.isEmpty();
    }


    @Override
    public String getRefreshToken() {
        return googleService.getRefreshTokenActive();
    }

    @Override
    public void saveRefreshToken() {
        String refreshToken = getRefreshToken();
    }
}
