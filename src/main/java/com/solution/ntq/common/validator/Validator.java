package com.solution.ntq.common.validator;


import com.solution.ntq.controller.request.AttendanceGroupRequest;


/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/08/5
 */
public final class Validator {
    private static final int CREATE_NEW_ATTENDANCE = -1;
    private static final long DAY_OF_THE_MONTH = 31;

    private Validator() {
    }

    public static boolean isTakeAttendance(AttendanceGroupRequest attendance) {
        return (attendance.getId() != CREATE_NEW_ATTENDANCE);
    }

    public static boolean isScopeOutOfOneMonth(long startDate, long endDate) {
        if (startDate == 0) {
            return false;
        }
        long diff = endDate - startDate;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays > DAY_OF_THE_MONTH;
    }
}
