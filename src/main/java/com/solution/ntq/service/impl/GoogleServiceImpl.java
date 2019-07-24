
package com.solution.ntq.service.impl;

import com.solution.ntq.common.until.GoogleUtils;
import com.solution.ntq.model.Token;
import com.solution.ntq.model.User;
import com.solution.ntq.service.base.GoogleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */

@NoArgsConstructor
@AllArgsConstructor
@Service
public class GoogleServiceImpl implements GoogleService {

    @Autowired
    private GoogleUtils googleUtils;


    public Token getToken(String code) {
        if (isCode(code)) {
            return null;
        } else {
            Token token = new Token();
            String tokenFormGoogle = getTokenFormGoogle(code);
            String accessToken = getAccessTokenFormGoogle(tokenFormGoogle);
            User user = getUserFormGoogle(accessToken);
            String refreshToken = getRefreshTokenFormGoogle(tokenFormGoogle);
            String idToken = getIdTokenFromGoogle(tokenFormGoogle);
            token.setAccessToken(accessToken);
            token.setUser(user);
            token.setRefreshToken(refreshToken);
            token.setIdToken(idToken);
            return token;
        }
    }

    @Override
    public String getTokenFormGoogle(String code) {
        try {
            return googleUtils.getToken(code);
        } catch (IOException ex) {
            return null;
        }
    }


    /**
     * Get Access token form google
     */

    @Override
    public String getAccessTokenFormGoogle(String response) {
        try {
            return googleUtils.getAccessToken(response);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Get Refresh token active form google
     */
    @Override
    public String getRefreshTokenFormGoogle(String response) {
        try {
            return googleUtils.getRefreshToken(response);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Get Refresh token active form google
     */
    @Override
    public String getIdTokenFromGoogle(String response) {
        try {
            return googleUtils.getIdToken(response);
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Get user information form google
     */

    @Override
    public User getUserFormGoogle(String accessToken) {
        try {
            return googleUtils.getUserInfo(accessToken);
        } catch (ParseException | IOException | NullPointerException ex) {
            return null;
        }
    }


    private boolean isCode(String code) {
        return (code == null || code.isEmpty());
    }
}

