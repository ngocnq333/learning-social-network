package com.solution.ntq.controller;

import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    /**
     * Get an user detail
     */
    @GetMapping("/api/v1/account/{id}")
    public ResponseEntity<User> getUserDetail(@PathVariable("id") String idUser) {
        User user = userService.getUserById(idUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
