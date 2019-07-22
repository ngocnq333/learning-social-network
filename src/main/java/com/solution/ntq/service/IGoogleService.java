package com.solution.ntq.service;

import com.solution.ntq.model.User;

import java.math.BigInteger;

public interface IGoogleService {
    String getAccessTokenFormGoogle(String response);
    String getRefreshTokenFormGoogle(String response);
    User getUserFormGoogle(String accessToken);
    boolean verifyUserNTQ(User user);
    boolean activeLoginToEmail(String code);
    String getToken(String code);
    String getAccessTokenActive();
    String getIdTokenFromGoogle(String response);
    User getUserActive();
    String getIdUserActive();
    String getTokenActive();


}
