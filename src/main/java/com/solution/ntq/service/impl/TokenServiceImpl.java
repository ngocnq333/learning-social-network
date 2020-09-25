package com.solution.ntq.service.impl;


import com.solution.ntq.repository.base.TokenRepository;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.service.base.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.solution.ntq.common.constant.GoogleLink.MINUTE_TIMEOUT;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private TokenRepository tokenRepository;

    @Override
    public Token getTokenByUserId(String userId) {
        return tokenRepository.findTokenByUserId(userId);
    }

    public boolean isTimeOut(Token token) {
        Date currentTime = new Date();
        long diff = currentTime.getTime() - token.getTime().getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        return (diffMinutes >= MINUTE_TIMEOUT);
    }

    @Override
    public void save(Token token){
            tokenRepository.save(token);
    }

}
