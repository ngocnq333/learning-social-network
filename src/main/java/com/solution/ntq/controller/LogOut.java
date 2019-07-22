package com.solution.ntq.controller;

import com.solution.ntq.service.ISignService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogOut {
    private ISignService iSignService;
    /**
     * Logout application
     */

    @GetMapping("/API/V1/logout")
    public ResponseEntity<Void> listAllCustomer(@RequestHeader("id_token") String idToken){
            // co the check id token o day
            iSignService.signOut();
            return new ResponseEntity<>(HttpStatus.OK);

    }
}
