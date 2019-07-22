package com.solution.ntq.controller;

import com.solution.ntq.model.User;
import com.solution.ntq.service.IGoogleService;
import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;


@Controller
@AllArgsConstructor
public class VerifyByGoogle {
    private ITokenService tokenService;
    @GetMapping("/home")
    public String listAllCustomer() {

            boolean ischeck = tokenService.isVerify();

            return "redirect:/home" ;

    }
}
