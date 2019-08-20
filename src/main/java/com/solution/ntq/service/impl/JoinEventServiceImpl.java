package com.solution.ntq.service.impl;

import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.response.AttendanceEventResponse;
import com.solution.ntq.repository.base.ClazzMemberRepository;
import com.solution.ntq.repository.base.JoinEventRepository;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.JoinEvent;
import com.solution.ntq.service.base.JoinEventService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Declare google service
 *
 * @author Nam_Phuong
 * @since 9/8/2019
 */

@AllArgsConstructor
@Service
public class JoinEventServiceImpl implements JoinEventService {
    private JoinEventRepository joinEventRepository;
    private ClazzMemberRepository clazzMemberRepository;

    @Override
    public List<AttendanceEventResponse> getListJointEvent(int eventId, int clazzId, String userId) {
        if (!isCaptain(clazzId, userId)) {
            throw new InvalidRequestException("Access deny");
        }
        List<JoinEvent> groupJoinEvent = joinEventRepository.getJoinEventsByEventIdAndJoinedTrue(eventId);
        return convertJoinEventToAttendanceGroup(groupJoinEvent);
    }

    private List<AttendanceEventResponse> convertJoinEventToAttendanceGroup(List<JoinEvent> groupJoinEvent) {
        List<AttendanceEventResponse> groupAttendanceResponse = new ArrayList<>();
        groupJoinEvent.forEach(joinEvent -> groupAttendanceResponse.add(eventResponseMapper(joinEvent)));
        return groupAttendanceResponse;
    }

    private AttendanceEventResponse eventResponseMapper(JoinEvent joinEvent) {
        AttendanceEventResponse attendanceEventResponse = new AttendanceEventResponse();
        attendanceEventResponse.setId(joinEvent.getId());
        attendanceEventResponse.setEventId(joinEvent.getEvent().getId());
        attendanceEventResponse.setAttendance(joinEvent.isAttendance());
        attendanceEventResponse.setUserId(joinEvent.getUser().getId());
        attendanceEventResponse.setUserName(joinEvent.getUser().getName());
        attendanceEventResponse.setEmail(joinEvent.getUser().getEmail());
        attendanceEventResponse.setPicture(joinEvent.getUser().getPicture());
        attendanceEventResponse.setNote(joinEvent.getNote());
        return attendanceEventResponse;
    }

    private boolean isCaptain(int clazzId, String userId) {
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId);
        if (clazzMember == null || StringUtils.isEmpty(userId)) {
            throw new InvalidRequestException("userIdError");
        }
        String userIdCaptain = clazzMember.getUser().getId();
        return userId.equals(userIdCaptain);
    }
}
