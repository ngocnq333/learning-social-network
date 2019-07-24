package com.solution.ntq.controller;

import com.solution.ntq.model.User;
import com.solution.ntq.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class UserController {
    private IUserService iUserService;
    /**
     * Get an user detail
     */
    @GetMapping(path = "/api/v1/account")
    public ResponseEntity<User> getUserDetail(HttpServletRequest request) {
            User user = iUserService.getUserById("109925868467939957281");
            return new ResponseEntity<> (user, HttpStatus.OK);
    }
}
