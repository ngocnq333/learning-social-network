package com.solution.ntq.controller;

import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.service.base.ClazzMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/classmembers")
public class ClazzMemberController {
    ClazzMemberService clazzMemberService;

    @GetMapping("/{idMember}")
    public ResponseEntity<Response<ClazzMemberResponse>> getClazzMember(@PathVariable("idMember") int idMember){
        ClazzMemberResponse clazzMemberResponse = clazzMemberService.getMember(idMember);
        Response<ClazzMemberResponse> responseResponse = new Response<>();
        responseResponse.setCodeStatus(HttpStatus.OK.value());
        responseResponse.setData(clazzMemberResponse);

        return new ResponseEntity<>(responseResponse, HttpStatus.OK);
    }
}
