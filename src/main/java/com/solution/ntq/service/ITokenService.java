package com.solution.ntq.service;

import com.solution.ntq.common.Token;

import java.math.BigInteger;


public interface ITokenService {
    boolean isTimeOut(String userId);
    void addToken(String userId, String token);
    Token getTokenByUserId(String userId);
    void clearAllToken();
    boolean isEmpty();
    String getRefreshToken();
    void saveRefreshToken();

}
