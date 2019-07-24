package com.solution.ntq.controller;

import com.solution.ntq.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */
@Controller
@AllArgsConstructor
public class GoogleController {
    private Environment env;

    /**
     * Login to application
     */
    @PostMapping(value = "/api/v1/google", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> signIn() {
        int codeStatus = 200;
        String linkGoogleApi = env.getProperty("url_google_api");
        Response response = new Response(codeStatus, linkGoogleApi);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
