package com.solution.ntq.common.validator;


import com.solution.ntq.controller.request.AttendanceGroupRequest;

import java.util.Date;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/08/5
 */
public class Validator {
    private static final int CREATE_NEW_ATTENDANCE = -1;

    private Validator() {
    }

    public static boolean isTakeAttendance(AttendanceGroupRequest attendance) {
        return (attendance.getId() != CREATE_NEW_ATTENDANCE);
    }


}
