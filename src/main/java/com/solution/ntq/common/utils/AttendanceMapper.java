package com.solution.ntq.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.controller.response.AttendanceResponse;
import com.solution.ntq.repository.entities.User;

public class AttendanceMapper {
    private AttendanceMapper() {}
    public static  <T> AttendanceResponse getAttendanceResponseMapObject(T element, User user) {
        AttendanceResponse attendanceResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        attendanceResponse = objectMapper.convertValue(element, AttendanceResponse.class);
        attendanceResponse.setUserId(user.getId());
        attendanceResponse.setUserName(user.getName());
        attendanceResponse.setPicture(user.getPicture());
        attendanceResponse.setEmail(user.getEmail());
        return attendanceResponse;
    }
}
