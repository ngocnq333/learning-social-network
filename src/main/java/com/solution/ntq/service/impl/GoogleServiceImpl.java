
package com.solution.ntq.service.impl;

import com.solution.ntq.common.GoogleUtils;
import com.solution.ntq.model.User;
import com.solution.ntq.service.IGoogleService;
import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class GoogleServiceImpl implements IGoogleService {
    private static final String NTQ_EMAIL_FORM = "ntq-solution.com.vn";
    private String token = "";
    private User user = new User();
    private String accessToken = "";
    private String idToken = "";
    @Autowired
    private GoogleUtils googleUtils;


    @Override
    public boolean activeLoginToEmail(String code) {
        if (isCode(code)) {
            return false;
        } else {
            token = getToken(code);
            accessToken = getAccessTokenFormGoogle(token);
            if (accessToken != null) {
                user = getUserFormGoogle(accessToken);
                return verifyUserNTQ(user);
            } else {
                return false;
            }
        }
    }


    @Override
    public User getUserActive() {
        return user;
    }

    @Override
    public String getIdUserActive() {
        return user.getId();
    }

    @Override
    public String getAccessTokenActive() {
        return accessToken;
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
    public String getToken(String code) {
        try {
            token = googleUtils.getToken(code);
            return token;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public String getTokenActive(){
        return token;
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
            return googleUtils.getIdAccessToken(response);
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

