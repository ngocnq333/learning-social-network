package com.solution.ntq.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.controller.response.AttendanceContentResponse;
import com.solution.ntq.repository.entities.User;

public final class AttendanceMapper {
    private AttendanceMapper() {}
    public static  <T> AttendanceContentResponse getAttendanceResponseMapObject(T element, User user) {
        AttendanceContentResponse attendanceContentResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        attendanceContentResponse = objectMapper.convertValue(element, AttendanceContentResponse.class);
        attendanceContentResponse.setUserId(user.getId());
        attendanceContentResponse.setUserName(user.getName());
        attendanceContentResponse.setPicture(user.getPicture());
        attendanceContentResponse.setEmail(user.getEmail());
        return attendanceContentResponse;
    }
}
