package com.solution.ntq.service.impl;

import com.solution.ntq.model.User;
import com.solution.ntq.service.ISignService;
import org.springframework.stereotype.Service;

@Service
public class SignServiceImpl implements ISignService {

    /**
     * Sign up user to application
     */
    @Override
    public void sigUp(User user) {
        String idUser = user.getId();
        if (isSignUp(idUser)) {
            // k lam gi ca
        } else {
            signUpUser(user);
        }
    }

    /**
     * Check user sign up by check is already have information in database
     */
    @Override
    public boolean isSignUp(String idUser) {
        return false;
    }

    /**
     * Sign up user to application
     */
    @Override
    public void signUpUser(User user) {
        // call to repo let sigup
    }
}
