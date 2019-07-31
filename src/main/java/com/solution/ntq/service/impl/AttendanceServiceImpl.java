package com.solution.ntq.service.impl;

import com.solution.ntq.repository.AttendanceRepository;
import com.solution.ntq.service.base.AttendanceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceRepository attendanceRepository;
}
