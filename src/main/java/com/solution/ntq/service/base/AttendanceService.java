package com.solution.ntq.service.base;

import com.solution.ntq.controller.request.AttendanceRequest;
import com.solution.ntq.controller.response.Response;


/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
public interface AttendanceService {
    void saveAttendanceGroup(AttendanceRequest attendanceGroupRequest);
}
