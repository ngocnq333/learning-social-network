package com.solution.ntq.controller;


import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.request.UserRequest;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.controller.response.UserResponse;
import com.solution.ntq.repository.base.TokenRepository;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


/**
 * @author Nam_Phuong
 * Delear google service
 * Date update 24/7/2019
 */

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1")
public class UserController {
    private UserService userService;
    private TokenRepository tokenRepository;

    /**
     * Get an user detail
     */
    @GetMapping("/account")
    public ResponseEntity<Response<User>> getUserDetail(@RequestAttribute("userId") String userId) {
        User user = userService.getUserById(userId);
        Response<User> response = new Response<>(ResponseCode.OK.value(),user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get an user detail
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<Response<UserResponse>> getUserDetails(@PathVariable("userId") String userId) {
        Response<UserResponse> response = new Response<>();
        try {
            UserResponse userResponse = userService.getUserResponseById(userId);
            response.setData(userResponse);
            response.setCodeStatus(ResponseCode.OK.value());
             return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setMessage(e.getMessage());
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *Update information of user
     */
    @PostMapping("/users/{userId}")
    public ResponseEntity<Response<UserRequest>> updateUser(@PathVariable("userId") String userIdUpdate, @RequestAttribute("userId") String idCurrentUser, @Valid @RequestBody UserRequest userRequest) {
        Response<UserRequest> response = new Response<>();
        try {
            userService.updateUser(idCurrentUser, userRequest, userIdUpdate);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<Response<List<UserResponse>>> getListUsersHaveEmail(@RequestParam (value = "userEmail") String userEmail){
        Response<List<UserResponse>> response = new Response<>();
        if (StringUtils.isBlank(userEmail)){
            response.setCodeStatus(ResponseCode.NO_CONTENT.value());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        try{
            response.setData(userService.findByEmailContains(userEmail));
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
