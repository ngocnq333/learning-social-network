package com.solution.ntq.controller;


import com.solution.ntq.response.IdUserTokenGoogle;
import com.solution.ntq.response.Response;
import com.solution.ntq.service.base.GoogleService;
import com.solution.ntq.service.base.SignService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */

@RestController
@AllArgsConstructor
public class TokenController {
    private GoogleService googleService;
    private SignService signService;

    /* *
     * Return status (token + value) of login by google
     * */
    @GetMapping("/login-google")
    public ResponseEntity<Response> signIn(@RequestParam(value = "code", defaultValue = "") String code) {
        if (googleService.verifyToken(code)) {
            signService.sigIn(googleService.getUserActive());
            String idUserCurrentSignIn = signService.idCurrentUserSignIn();
            String idToken = googleService.getIdTokenActive();
            IdUserTokenGoogle idUserTokenGoogle = new IdUserTokenGoogle(idUserCurrentSignIn, idToken);
            return new ResponseEntity<>(new Response<>(200, idUserTokenGoogle), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Logout application
     */

    @DeleteMapping("/api/v1/token")
    public ResponseEntity<Response> signOut(@RequestHeader("id_token") String idToken) {
        Response<String> response = signService.signOut(idToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
