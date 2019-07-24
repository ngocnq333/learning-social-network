package com.solution.ntq.service.base;

import com.solution.ntq.model.Token;
import com.solution.ntq.model.User;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */
public interface GoogleService {
    Token getToken(String code);

    String getAccessTokenFormGoogle(String response);

    String getRefreshTokenFormGoogle(String response);

    User getUserFormGoogle(String accessToken);

    String getTokenFormGoogle(String code);

    String getIdTokenFromGoogle(String response);
}
