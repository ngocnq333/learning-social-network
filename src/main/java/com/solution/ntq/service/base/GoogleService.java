package com.solution.ntq.service.base;

import com.solution.ntq.repository.entities.User;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */
public interface GoogleService {
    boolean verifyToken(String code);

    String getAccessTokenFormGoogle(String response);

    String getRefreshTokenFormGoogle(String response);

    User getUserFormGoogle(String accessToken);

    boolean verifyUserNTQ(User user);

    String getTokenFormGoogle(String code);

    String getAccessTokenActive();

    String getIdTokenFromGoogle(String response);

    User getUserActive();

    String getIdUserActive();

    String getTokenActive();

    String getIdTokenActive();

    String getRefreshTokenActive();


}
