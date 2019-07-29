package com.solution.ntq.controller;

import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.TokenRepository;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.ClazzService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin
/**
 * @author Duc Anh
 */
@RequestMapping("/api/v1/classes")
public class ClazzController {


    private ClazzService clazzService;

    /**
     * fix data of application
     *
     * @return
     * @throws ParseException
     */

    private TokenRepository tokenRepository;

    @GetMapping
    public ResponseEntity<Response<List<ClazzResponse>>> getListClassByUserId( @RequestParam(value = "userId",defaultValue = "")  String userId) {
        Response<List<ClazzResponse>> response = new Response<>();
        response.setCodeStatus(HttpStatus.OK.value());
        response.setData(clazzService.getClassByUser(userId));
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<Response<ClazzResponse>>getClassById(@PathVariable("classId") int clazzId,@RequestHeader("id_token") String idToken ) {
        Token token = tokenRepository.findTokenByIdToken(idToken);
        String userId = token.getUser().getId();
        for ( ClazzMember member :clazzService.getClassById(clazzId).getClazzMember())
        {
            if(member.getUser().getId().contains(userId))
            {
                clazzService.getClassById(clazzId).setJoin(true);
            }
        }
        Response<ClazzResponse> response = new Response<>();
        response.setCodeStatus(HttpStatus.OK.value());
        response.setData(clazzService.getClassById(clazzId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/add-data")
    public ResponseEntity<String> addData() throws ParseException {
        clazzService.addAllData();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
