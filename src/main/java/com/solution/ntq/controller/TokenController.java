package com.solution.ntq.controller;


import com.solution.ntq.repository.TokenRepository;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.service.base.SignService;
import lombok.AllArgsConstructor;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */

@Controller
@AllArgsConstructor
@CrossOrigin
public class TokenController {
    private SignService signService;
    private TokenRepository tokenRepository;

    /* *
     * Return status (token + value) of login by google
     * */
    @GetMapping("/login-google")
    public String signIn(@RequestParam(value = "code", defaultValue = "") String code) {
        Token token = signService.sigIn(code);
        try {
            String idToken = token.getIdToken();
            return ("redirect:http://localhost:4200/login?idToken="+ idToken );
        }catch (InvalidDataAccessApiUsageException ex) {
            return ("redirect:http://localhost:4200/login?status="+ "mailInvalid");
        }catch (Exception ex) {
            return ("redirect:http://localhost:4200/login?status"+ "fale");
        }
    }

}

