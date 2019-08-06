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

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/attendances")
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
}
