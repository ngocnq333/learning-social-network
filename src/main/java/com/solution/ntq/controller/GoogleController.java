package com.solution.ntq.controller;

import com.solution.ntq.model.Token;
import com.solution.ntq.model.User;
import com.solution.ntq.repository.base.TokenRepository;
import com.solution.ntq.response.Response;
import com.solution.ntq.service.base.SignService;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */
@RestController
@AllArgsConstructor
@CrossOrigin
public class GoogleController {
    private Environment env;
    private SignService signService;
    private TokenRepository tokenRepository;

    /**
     * Login to application
     */
    @GetMapping(value = "/api/v1/google-link", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> signIn() {
        String linkGoogleApi = env.getProperty("url_google_api");
        return new Response<>(HttpStatus.OK, linkGoogleApi);
    }

    /**
     * Logout application
     */
    @DeleteMapping("/api/v1/logout")
    public Response<String> signOut(@RequestHeader("id_token") String idToken) {
        Token token = tokenRepository.findTokenByIdToken(idToken);
        return signService.signOut(token.getUser().getId());
    }
}
