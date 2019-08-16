package com.solution.ntq.service.base;

import com.solution.ntq.controller.request.AttendanceRequest;
import com.solution.ntq.controller.response.AttendanceContentResponse;

import java.util.List;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
public interface AttendanceService {
    List<AttendanceContentResponse> getListAttendance (int contentId);
    void saveAttendanceGroup(AttendanceRequest attendanceGroupRequest);
    List getListAttendanceByClassId(int classId, String title,String type);

}
