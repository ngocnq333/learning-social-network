package com.solution.ntq.controller;

import com.solution.ntq.model.User;
import com.solution.ntq.service.ISignService;
import com.solution.ntq.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */

@RestController
@AllArgsConstructor
public class UserController {
    private IUserService iUserService;
    private ISignService iSignService;

    /**
     * Get an user detail
     */
    @GetMapping(path = "/api/v1/account")
    public ResponseEntity<User> getUserDetail() {
        String idUserCurrentSignIn = iSignService.idCurrentUserSignIn();
        User user = iUserService.getUserById(idUserCurrentSignIn);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
