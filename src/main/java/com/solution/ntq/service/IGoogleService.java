package com.solution.ntq.service;

import com.solution.ntq.model.User;

public interface IGoogleService {
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
