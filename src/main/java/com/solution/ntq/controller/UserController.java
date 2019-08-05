package com.solution.ntq.controller;


import com.solution.ntq.controller.response.Response;
import com.solution.ntq.repository.TokenRepository;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.UserService;
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
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    private TokenRepository tokenRepository;

    /**
     * Get an user detail
     */
    @GetMapping("/api/v1/account")
    public ResponseEntity<Response<User>> getUserDetail(@RequestHeader("id_token") String idToken) {
        Token token = tokenRepository.findTokenByIdToken(idToken);
        User user = userService.getUserById(token.getUser().getId());
        Response<User> response = new Response<>(HttpStatus.OK.value(),user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get an user detail
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Response<User>> getUserDetails(@PathVariable("userId") String userId) {
        User user = userService.getUserById(userId);
        Response<User> response = new Response<>(HttpStatus.OK.value(),user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/Token/{idUser}")
    public Response getToken(@PathVariable("idUser") String idUser) {
        Token token = tokenRepository.findTokenByUserId(idUser);
        if (token == null) {
            return new Response<>(HttpStatus.OK.value(), token);
        } else {
            return new Response<>(HttpStatus.NOT_FOUND.value(), null);
        }
    }
}
