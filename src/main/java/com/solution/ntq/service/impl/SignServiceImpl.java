package com.solution.ntq.service.impl;

import com.solution.ntq.model.User;
import com.solution.ntq.repository.base.UserRepository;
import com.solution.ntq.response.Response;
import com.solution.ntq.service.base.GoogleService;
import com.solution.ntq.service.base.SignService;
import com.solution.ntq.service.base.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Nam_Phuong
 * Delear sign service
 * Date update 24/7/2019
 */

@Service
@AllArgsConstructor
public class SignServiceImpl implements SignService {
    private UserRepository iUserRepository;
    private TokenService iTokenService;
    private GoogleService iGoogleService;

    /**
     * Sign up user to application
     */
    @Override
    public void sigIn(User user) {
        String idUser = user.getId();
        String idToken = iGoogleService.getIdTokenActive();
        iTokenService.addToken(idUser, idToken);
        if (!isSignUp(idUser)) {
            signUpUser(user);
        }
    }

    /**
     * Sign out application
     */
    @Override
    public Response<String> signOut(String idToken) {
        try {
            iTokenService.clearIdToken(idToken);
            return new Response<String>(200, null);
        } catch (Exception ex) {
            return new Response<String>(0, null);
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
        // Can viet them method de luu id_token
    }

    /**
     * Get id of user current sign in
     */
    @Override
    public String idCurrentUserSignIn() {
        return iGoogleService.getIdUserActive();
    }
}
