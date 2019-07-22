package com.solution.ntq.service.impl;

import com.solution.ntq.model.User;
import com.solution.ntq.repository.IUserRepository;
import com.solution.ntq.service.IGoogleService;
import com.solution.ntq.service.ISignService;
import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class SignServiceImpl implements ISignService {

    private IUserRepository iUserRepository;

    private ITokenService iTokenService;

    private IGoogleService iGoogleService;

    /**
     * Sign up user to application
     */
    @Override
    public void sigIn(User user) {
        String idUser = user.getId();
        String idToken = iGoogleService.getIdTokenActive();
        iTokenService.addToken(idUser,idToken);
        if (!isSignUp(idUser)) {
            signUpUser(user);
        }
    }

    /** Sign out application*/
    @Override
    public void signOut(String idToken) {
        iTokenService.clearIdToken(idToken);
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
}
