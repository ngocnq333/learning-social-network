package com.solution.ntq.controller;

import com.solution.ntq.service.IGoogleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Controller
@AllArgsConstructor
public class Login {

    static final String URL_GOOGLE_API = "https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/login-google&response_type=code&client_id=80724656105-fg2ndheoujm7c7dd4ob1i9mq3ebdbjhb.apps.googleusercontent.com&approval_prompt=force";

    /*http://localhost:8080/API/V1/login*/
    //-----Retrieve All Customer
    @GetMapping("/API/V1/login")
    public String listAllCustomer() {
        return "redirect:/" + URL_GOOGLE_API;
    }
}
