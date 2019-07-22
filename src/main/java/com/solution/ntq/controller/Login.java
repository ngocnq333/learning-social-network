package com.solution.ntq.controller;

import com.solution.ntq.repository.IClassMemberRepository;
import com.solution.ntq.repository.NtqClassRepository;

import com.solution.ntq.service.IGoogleService;
import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class Login {
    private static final String URL_GOOGLE_API = "https://accounts.google.com/o/oauth2/auth?scope=openid%20profile%20email&redirect_uri=http://localhost:8080/login-google&response_type=code&client_id=80724656105-fg2ndheoujm7c7dd4ob1i9mq3ebdbjhb.apps.googleusercontent.com&approval_prompt=force&access_type=offline";
    private IGoogleService iGoogleService;
    private ITokenService iTokenService;
    /**
     * Login to application
     */
    @Autowired
    NtqClassRepository repository;
    @Autowired
    IClassMemberRepository repository1;
    @GetMapping("/API/V1/login")
    public String listAllCustomer() {

        return "redirect:" + URL_GOOGLE_API;
    }


    /* *
     * Return status (token + value) of login by google
     * */
    @GetMapping(path = "/login-google")
    public String listAllCustomer(@RequestParam(value = "code", defaultValue = "") String code) {
        if (iGoogleService.activeLoginToEmail(code)) {
            String tokenActive = iGoogleService.getAccessTokenActive();
            return "redirect:" + "http://localhost:4200/callback?token=" + tokenActive;
        } else return "Forbidden";
    }
}
