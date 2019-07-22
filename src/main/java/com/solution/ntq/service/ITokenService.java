package com.solution.ntq.service;

import com.solution.ntq.common.Token;



public interface ITokenService {
    boolean isTimeOut(String userId);
    void addToken(String userId, String idToken);
    Token getTokenByUserId(String userId);
    void clearAllToken();
    boolean isEmpty();
    String getRefreshToken();
    void saveRefreshToken();
    boolean isVerify();

}
