package com.solution.ntq.controller;


import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.service.base.SignService;
import lombok.AllArgsConstructor;

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

    /* *
     * Return status (token + value) of login by google
     * */
    @GetMapping("/login-google")
    public String signIn(@RequestParam(value = "code", defaultValue = "") String code) {
        try {
            Token token = signService.sigIn(code);
            String idToken = token.getIdToken();
            return ("redirect:http://localhost:4200/login?idToken="+ idToken );
        }catch (InvalidRequestException ex) {
            return ("redirect:http://localhost:4200/login?status="+ "mailInvalid");
        }catch (Exception ex) {
            return ("redirect:http://localhost:4200/login?status"+ "false");
        }
    }

}

