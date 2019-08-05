package com.solution.ntq.common.validator;

import com.solution.ntq.common.constant.Level;
import com.solution.ntq.controller.request.AttendanceGroupRequest;
import com.solution.ntq.controller.request.ContentRequest;

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


    public static boolean isValidContentRequest(ContentRequest contentRequest) {

        if (contentRequest.getStartDate().before(new Date())) {
            return false;
        }
        if (contentRequest.getEndDate().before(contentRequest.getStartDate())) {
            return false;
        }

        return !(!contentRequest.getLevel().equalsIgnoreCase(Level.BEGINNER.value()) && !contentRequest.getLevel().equalsIgnoreCase(Level.INTERMEDISE.value())
                && !contentRequest.getLevel().equalsIgnoreCase(Level.EXPERT.value()));
    }
}
