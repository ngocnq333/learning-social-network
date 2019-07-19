package com.solution.ntq.controller;

import com.solution.ntq.model.User;
import com.solution.ntq.service.IGoogleService;
import lombok.AllArgsConstructor;
import org.apache.http.client.ClientProtocolException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class VerifyByGoogle {
    private IGoogleService iGoogleService;

    /* *
     * Return status (token + value) of login by google
     * */
    @GetMapping(path = "/login-google")
    public ResponseEntity<String> listAllCustomer(@RequestParam(value = "code", defaultValue = "") String code){
        if (iGoogleService.activeLoginToEmail(code)){
            String tokenAccount = iGoogleService.getAccessTokenFormGoogle(code);
            return new ResponseEntity<>(tokenAccount,HttpStatus.OK);
        } else  return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
