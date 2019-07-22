
package com.solution.ntq.service.impl;

import com.solution.ntq.common.GoogleUtils;
import com.solution.ntq.model.User;
import com.solution.ntq.service.IGoogleService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@NoArgsConstructor
@AllArgsConstructor
@Service
public class GoogleServiceImpl implements IGoogleService {
    private static final String NTQ_EMAIL_FORM = "ntq-solution.com.vn";
    private static String token;
    private static String accessToken;
    private static String idToken;
    private static User user = new User();

    @Autowired
    private GoogleUtils googleUtils;


    /**
     * Verify token
     */
    @Override
    public boolean verifyToken(String code) {
        if (isCode(code)) {
            return false;
        } else {
            token = getTokenFormGoogle(code);
            accessToken = getAccessTokenFormGoogle(token);
            if (accessToken != null) {
                user = getUserFormGoogle(accessToken);
                return verifyUserNTQ(user);
            } else {
                return false;
            }
        }
    }

    /**
     * Get token Active
     */
    @Override
    public String getTokenActive() {
        return token;
    }

    /**
     * Get User active
     */
    @Override
    public User getUserActive() {
        return user;
    }

    /**
     * Get Id of user
     */
    @Override
    public String getIdUserActive() {
        return user.getId();
    }

    /**
     * Get access token active
     */
    @Override
    public String getAccessTokenActive() {
        return accessToken;
    }

    @Override
    public String getRefreshTokenActive() {
        return getRefreshTokenFormGoogle(token);
    }

    @Override
    public String getIdTokenActive(){
        idToken = getIdTokenFromGoogle(token);
        return idToken;
    }
    /**
     * Verify email of ntq
     */

    @Override
    public boolean verifyUserNTQ(User user) {
        try {
            String suffixEmail = user.getHd();
            return (isNTQMail(suffixEmail));
        } catch (Exception e) {
            return false;
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
     * Get id_token form google
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
        } catch (IOException ex) {
            return null;
        }
    }


    /**
     * Verify mail ntq with mail gmail
     */

    private boolean isNTQMail(String userEmail) {
        return (NTQ_EMAIL_FORM.equalsIgnoreCase(userEmail));
    }

    /**
     * Check code recovery code form google
     */

    private boolean isCode(String code) {
        return (code == null || code.isEmpty());
    }
}

