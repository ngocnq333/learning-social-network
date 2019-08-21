package com.solution.ntq.controller;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.request.ClazzMemberRequest;
import com.solution.ntq.controller.request.ClazzRequest;
import com.solution.ntq.controller.request.MemberRequest;
import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.service.base.AttendanceService;
import com.solution.ntq.service.base.ClazzService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Nam truong
 * @version 1.0.1
 * @since 19/Aug/2019
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/v1/classes")
public class ClazzController {
    private ClazzService clazzService;
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<Response<List<ClazzResponse>>> getListClazzByUserId(@RequestParam(value = "userId", defaultValue = "") String userId) {
        Response<List<ClazzResponse>> response = new Response<>();
        response.setCodeStatus(ResponseCode.OK.value());
        response.setData(clazzService.getClazzByUser(userId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<Response<ClazzResponse>> getClazzById(@PathVariable("classId") int clazzId, @RequestAttribute("userId") String idCurrentUser) {
        Response<ClazzResponse> response = new Response<>();
        try {
            ClazzResponse clazzResponse = clazzService.getClazzById(clazzId, idCurrentUser);
            response.setCodeStatus(ResponseCode.OK.value());
            response.setData(clazzResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setMessage(e.getMessage());
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {

            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{classId}/members")
    public ResponseEntity<Response<List<ClazzMemberResponse>>> getListMemberOfClazz(@PathVariable(value = "classId") int clazzId,
                                                                                    @RequestParam(name = "status", defaultValue = "") String status) {
        Response<List<ClazzMemberResponse>> response = new Response<>();
        try {
            List<ClazzMemberResponse> clazzMemberResponseList = clazzService.findAllMemberByClazzId(clazzId, status);
            response.setCodeStatus(ResponseCode.OK.value());
            response.setData(clazzMemberResponseList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException ex) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            response.setMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{classId}/users")
    public ResponseEntity<Response<ClazzMemberResponse>> addClazzMember(@RequestBody MemberRequest memberRequest,
                                                                        @PathVariable(value = "classId") int clazzId,
                                                                        @RequestAttribute("userId") String idCurrentUser) {
        Response<ClazzMemberResponse> response = new Response<>();
        ClazzMemberResponse memberResponse;
        try {
            memberResponse = clazzService.addClazzMember(memberRequest, clazzId, idCurrentUser);
            response.setCodeStatus(ResponseCode.OK.value());
            response.setData(memberResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException ex) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{classId}/users/{userId}")
    public ResponseEntity<Response<ClazzMemberRequest>> updateRoleForClazzMember(@RequestAttribute("userId") String idCurrentUser, @NotNull @PathVariable("classId") int clazzId
            , @PathVariable("userId") String userIdUpdate) {
        Response<ClazzMemberRequest> response = new Response<>();
        try {
            clazzService.updateCaptainForClazz(clazzId, idCurrentUser, userIdUpdate);
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

    /**
     * Update information of Clazz
     */
    @PostMapping("/{classId}")
    public ResponseEntity<Response<ClazzRequest>> updateClazz(@PathVariable("classId") int clazzId, @RequestAttribute("userId") String idCurrentUser, @Valid @RequestBody ClazzRequest clazzRequest) {
        Response<ClazzRequest> response = new Response<>();
        try {
            clazzService.updateClazz(idCurrentUser, clazzRequest, clazzId);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{classId}/members/{memberId}")
    public ResponseEntity<Response> deleteClazzMember(@PathVariable(name = "classId") int clazzId, @PathVariable(name = "memberId") String memberId, @RequestAttribute("userId") String userId) {
        Response response = new Response();
        try {
            clazzService.deleteMember(clazzId, userId, memberId);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalAccessException ex) {
            response.setMessage(ex.getMessage());
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            response.setMessage(ex.getMessage());
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{classId}/attendances")
    public ResponseEntity<Response<List>> getListContentAttendanceByClazz(@PathVariable("classId") int clazzId
            , @RequestParam(value = "title", defaultValue = "") String title
            , @RequestParam(value = "type", defaultValue = "content") String type) {
        Response<List> response = new Response<>();
        try {
            List attendanceResponseList = attendanceService.getListAttendanceByClazzId(clazzId, title, type);
            response.setCodeStatus(HttpStatus.OK.value());
            response.setData(attendanceResponseList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{classId}/members/{memberId}/status/JOINED")
    public ResponseEntity<Response<ClazzMemberResponse>> updateStatusMember(@RequestAttribute("userId") String idCurrentUser,
                                                                            @PathVariable("classId") int clazzId,
                                                                            @PathVariable("memberId") String memberId) {
        Response<ClazzMemberResponse> response = new Response<>();
        try {
            response.setData(clazzService.updateStatusMember(idCurrentUser, clazzId, memberId));
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException ex) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
