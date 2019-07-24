package com.solution.ntq.service;

import com.solution.ntq.model.User;

/**
 * @author Nam_Phuong
 * Delear sign service
 * Date update 24/7/2019
 */
public interface ISignService {

    /**
     * Sign out application
     */
    void signOut(String idToken);

    /**
     * Sign up service
     */

    void sigIn(User user);

    /**
     * Check user sign up in application
     */
    boolean isSignUp(String idUser);

    /**
     * Sign up user in database
     */
    void signUpUser(User user);

    String idCurrentUserSignIn();
}
