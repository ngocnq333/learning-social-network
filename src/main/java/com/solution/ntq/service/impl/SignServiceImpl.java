package com.solution.ntq.service.impl;

import com.solution.ntq.common.constant.GoogleLink;
import com.solution.ntq.model.Token;
import com.solution.ntq.model.User;
import com.solution.ntq.repository.base.TokenRepository;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.response.Response;
import com.solution.ntq.service.base.GoogleService;
import com.solution.ntq.service.base.SignService;
import lombok.AllArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Nam_Phuong
 * Delear sign service
 * Date update 24/7/2019
 */

@Service
@AllArgsConstructor
public class SignServiceImpl implements SignService {
    private UserRepository iUserRepository;
    private GoogleService iGoogleService;
    private TokenRepository tokenRepository;

    /**
     * Sign up user to application
     */
    @Override
    public Token sigIn(String code) {
        Token token = iGoogleService.getToken(code);
        if (token != null && token.getUser() != null && token.getIdToken() != null &&
                token.getRefreshToken() != null
                && verifyToken(token)) {
            try {
                Token token1 = tokenRepository.findTokenByUserId(token.getUser().getId());

                if (token1 != null) {
                    tokenRepository.removeTokenById(token1.getId());
                }
                String idUser = token.getUser().getId();
                if (!isSignUp(idUser)) {
                    signUpUser(token.getUser());
                }
                token.setTime(new Date());
                tokenRepository.save(token);
                return token;
            } catch (InvalidDataAccessApiUsageException ex) {
                return null;
            }
        }
        return null;
    }

    /**
     * Sign out application
     *
     * @return
     */
    @Override
    public Response<String> signOut(String userId) {
        try {
            Token token = tokenRepository.findTokenByUserId(userId);
            if (token == null) {
                return new Response<>(HttpStatus.BAD_REQUEST, null);
            } else {
                tokenRepository.removeTokenById(token.getId());
                return new Response<>(HttpStatus.OK, "done");
            }
        } catch (Exception ex) {
            return new Response<>(HttpStatus.NOT_FOUND, ex.toString());
        }
    }

    /**
     * Check user sign up by check is already have information in database
     */
    @Override
    public boolean isSignUp(String idUser) {
        return (iUserRepository.existsById(idUser));
    }

    /**
     * Sign up user to application
     */
    @Override
    public void signUpUser(User user) {
        iUserRepository.save(user);
    }

    /**
     * Verify token
     */

    private boolean verifyToken(Token token) {
        String accessToken = token.getAccessToken();
        if (accessToken != null) {
            User user = token.getUser();
            return verifyUserNTQ(user);
        } else {
            return false;
        }
    }


    /**
     * Verify email of ntq
     */
    private boolean verifyUserNTQ(User user) {
        try {
            String suffixEmail = user.getHd();
            return (isNTQMail(suffixEmail));
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Verify mail ntq with mail gmail
     */
    private boolean isNTQMail(String userEmail) {
        return (GoogleLink.NTQ_EMAIL_FORM.equalsIgnoreCase(userEmail));
    }

}
