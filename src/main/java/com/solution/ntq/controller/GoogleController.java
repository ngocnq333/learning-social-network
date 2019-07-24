package com.solution.ntq.controller;

import com.solution.ntq.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */
@RestController
@AllArgsConstructor
public class GoogleController {
    private Environment env;

    /**
     * Login to application
     */
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/api/v1/google")
    public ResponseEntity<Response> signIn() {
        int codeStatus = 200;
        String linkGoogleApi = env.getProperty("url_google_api");
        Response<String> response = new Response<>(codeStatus, linkGoogleApi);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
