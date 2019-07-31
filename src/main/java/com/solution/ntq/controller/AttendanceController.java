package com.solution.ntq.controller;

import com.solution.ntq.service.base.AttendanceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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
}
