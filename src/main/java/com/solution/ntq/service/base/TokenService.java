package com.solution.ntq.service.base;

import com.solution.ntq.repository.entities.Token;

import java.io.IOException;

public interface TokenService {
    Token getTokenByUserId(String userId);

    boolean isTimeOut(Token token);

    void save(Token token) throws IOException;
}
