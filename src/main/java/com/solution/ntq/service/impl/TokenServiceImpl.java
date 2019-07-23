package com.solution.ntq.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.solution.ntq.common.Token;
import com.solution.ntq.service.IGoogleService;
import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements ITokenService {
    private static final Map<String, Token> tokenList = new HashMap<>();
    private static final int MINUTE_TIMEOUT = 1;
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = new JacksonFactory();

    @Autowired
    private IGoogleService iGoogleService;

    public boolean isVerify() {
        try {
            String token = iGoogleService.getTokenActive();
            String idToken = iGoogleService.getIdTokenFromGoogle(token);
             verifyAccessToken(idToken);
             return true;
        }catch (IOException|GeneralSecurityException ex) {
            return false;
        }
    }

    public void verifyAccessToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("80724656105-fg2ndheoujm7c7dd4ob1i9mq3ebdbjhb.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

// (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...

        } else {
            System.out.println("Invalid ID token.");
        }
    }

    /**
     * @param userId userid
     * @return true if time out
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
     * @param token
     */
    @Override
    public void addToken(String userId, String token) {
        try {
            Token tokenNew = new Token(new Date(), token);
            tokenList.put(userId, tokenNew);
        } catch (NullPointerException ex) {
            clearAllToken();
        }

    }

    /**
     * Clear all token have in list
     */
    @Override
    public void clearAllToken() {
        tokenList.clear();
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
        return null;
    }

    @Override
    public void saveRefreshToken() {
        String refreshToken = getRefreshToken();

    }
}
