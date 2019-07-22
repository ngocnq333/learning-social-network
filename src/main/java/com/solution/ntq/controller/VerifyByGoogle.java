package com.solution.ntq.controller;

import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class VerifyByGoogle {
    private ITokenService tokenService;

    @GetMapping("/home")
    public String listAllCustomer() {

        //    boolean ischeck = tokenService.isVerify();

        return "redirect:/home";

    }
}
