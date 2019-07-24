package com.solution.ntq.controller;


import com.solution.ntq.service.IGoogleService;
import com.solution.ntq.service.ISignService;
import com.solution.ntq.service.ITokenService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@AllArgsConstructor
public class SignController {

    private IGoogleService iGoogleService;
    private ISignService iSignService;
    private Environment env;

    /**
     * Login to application
     */
    @GetMapping(value = "/api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap> signIn() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("url_link",env.getProperty("url_google_api"));
        return new ResponseEntity<>((HashMap) hashMap, HttpStatus.OK);
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

    /**
     * Logout application
     */

    @GetMapping("/api/v1/logout")
    public ResponseEntity<Void> listAllCustomer(@RequestHeader("id_token") String idToken){
        // co the check id token o day
        iSignService.signOut(idToken);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
