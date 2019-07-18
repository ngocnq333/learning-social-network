package com.solution.ntq.controller;

import com.solution.ntq.service.IGoogleService;
import lombok.AllArgsConstructor;
import org.apache.http.client.ClientProtocolException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class VerifyByGoogle {
    private IGoogleService iGoogleService;

    /**
     * Return status (token + value) of login by google
     */
   /* @RequestMapping(value = "/login-google", method = RequestMethod.GET)
    public ResponseEntity<String> listAllCustomer(@RequestParam("code") String code) {
        if (iGoogleService.verifyUser(code)) {

        }

        return new ResponseEntity<String>(, HttpStatus.OK);
    }*/
}
