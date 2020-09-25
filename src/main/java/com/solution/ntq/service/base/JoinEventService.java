package com.solution.ntq.service.base;

import com.solution.ntq.controller.response.AttendanceEventResponse;

import java.util.List;

/**
 * @author Nam_Phuong
 * Delear google service
 * @since  update 24/7/2019
 */
public interface JoinEventService {
    List<AttendanceEventResponse> getListJointEvent(int eventId, int clazzId,String userId);
}
