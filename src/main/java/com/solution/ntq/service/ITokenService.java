package com.solution.ntq.service;

import com.solution.ntq.common.Token;

/**
 * @author Nam_Phuong
 * Delear token service
 * Date update 24/7/2019
 */
public interface ITokenService {
    boolean isTimeOut(String userId);

    void addToken(String userId, String idToken);

    Token getTokenByUserId(String userId);

    void clearIdToken(String idToken);

    boolean isEmpty();

    String getRefreshToken();

    void saveRefreshToken();


}
