package com.solution.ntq.controller;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.request.ClazzMemberRequest;
import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.repository.ClazzRepository;
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

    @GetMapping
    public ResponseEntity<Response<List<ClazzResponse>>> getListClassByUserId( @RequestParam(value = "userId",defaultValue = "")  String userId) {
        Response<List<ClazzResponse>> response = new Response<>();
        response.setCodeStatus(HttpStatus.OK.value());
        response.setData(clazzService.getClassByUser(userId));
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<Response<ClazzResponse>> getClassById(@PathVariable("classId") int clazzId, @RequestHeader("id_token") String tokenId) {
        ClazzResponse clazzResponse = clazzService.getClassById(clazzId, tokenId);
        Response<ClazzResponse> response = new Response<>();
        response.setCodeStatus(HttpStatus.OK.value());
        response.setData(clazzResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/add-data")
    public ResponseEntity<String> addData() throws ParseException {
        clazzService.addAllData();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/{classId}/users/{userId}")
    public ResponseEntity<Response<ClazzMemberRequest>> updateRoleForClassMember(@RequestHeader("id_token") String idToken, @PathVariable("classId") int classId
            , @PathVariable("userId") String userId) {
        Response<ClazzMemberRequest> response = new Response<>();
        try {
            clazzService.setCaptainForClass(classId, idToken, userId);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {

            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
