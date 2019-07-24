package com.solution.ntq.service.base;

import com.solution.ntq.model.Token;
import com.solution.ntq.model.User;
import com.solution.ntq.response.Response;

/**
 * @author Nam_Phuong
 * Delear sign service
 * Date update 24/7/2019
 */
public interface SignService {

    /**
     * Sign out application
     */
    Response<String> signOut(String userId);

    /**
     * Sign up service
     */

    Token sigIn(String code);

    /**
     * Check user sign up in application
     */
    boolean isSignUp(String idUser);

    /**
     * Sign up user in database
     */
    void signUpUser(User user);


}
