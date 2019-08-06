package com.solution.ntq.controller;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.request.AttendanceRequest;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.service.base.AttendanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin
public class AttendanceController {
    private AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<Response<String>> addAttendance(@Valid @RequestBody AttendanceRequest attendanceRequest) {
        Response<String> response = new Response<>();
        try {
            attendanceService.saveAttendanceGroup(attendanceRequest);
            response.setCodeStatus(ResponseCode.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException ex) {
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            response.setData(ex.toString());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping
    public ResponseEntity<Response<List<AttendanceResponse>>>  getListAttendance(@RequestParam(value = "contentId") int contentId) {
        Response<List<AttendanceResponse>> response = new Response<>();
        try {
            response.setCodeStatus(HttpStatus.OK.value());
            List<AttendanceResponse> attendanceResponseList = attendanceService.getListAttendance(contentId);
            response.setData(attendanceResponseList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (InvalidRequestException ex){
            response.setCodeStatus(ResponseCode.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            response.setCodeStatus(ResponseCode.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
