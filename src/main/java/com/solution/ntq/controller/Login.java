package com.solution.ntq.controller;


import com.solution.ntq.service.IGoogleService;
import com.solution.ntq.service.ISignService;
import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
@AllArgsConstructor
public class Login {

    private IGoogleService iGoogleService;
    private ITokenService iTokenService;
    private ISignService iSignService;
    private Environment env;

    /**
     * Login to application
     */
    @GetMapping("/API/V1/login")
    public String signIn() {
        return "redirect:" + env.getProperty("url_google_api");
    }

    /* *
     * Return status (token + value) of login by google
     * */
    @GetMapping(path = "/login-google")
    public ResponseEntity<String> signIn(@RequestParam(value = "code", defaultValue = "") String code) {
        if (iGoogleService.verifyToken(code)) {
            iSignService.sigIn(iGoogleService.getUserActive());
            String idToken = "{\"id_token\" : \"" + iGoogleService.getIdTokenActive() + "\"\n" + "}";
            return new ResponseEntity<>(idToken, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
