package com.solution.ntq.service.impl;

import com.solution.ntq.common.constant.Constant;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.AttendanceMapper;
import com.solution.ntq.common.validator.Validator;
import com.solution.ntq.controller.request.AttendanceGroupRequest;
import com.solution.ntq.controller.request.AttendanceRequest;
import com.solution.ntq.controller.response.AttendanceContentResponse;
import com.solution.ntq.controller.response.AttendanceEventResponse;
import com.solution.ntq.repository.base.*;
import com.solution.ntq.repository.entities.*;
import com.solution.ntq.service.base.AttendanceService;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
@AllArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {
    private AttendanceRepository attendanceRepository;
    private ClazzMemberRepository clazzMemberRepository;
    private EventRepository eventRepository;
    private UserService userService;
    private ContentRepository contentRepository;
    private ClazzService clazzService;
    private JoinEventRepository joinEventRepository;

    @Override
    @Transactional
    public void saveAttendanceGroup(AttendanceRequest attendanceRequest) {
        String userId = attendanceRequest.getUserId();
        int clazzId = attendanceRequest.getClazzId();
        boolean isCaption = clazzService.isCaptainClazz(userId, clazzId);
        if (isCaption) {
            List<AttendanceGroupRequest> attendancesGroup = attendanceRequest.getAttendanceGroupRequests();
            saveAttendance(attendancesGroup);
        } else {
            throw new InvalidRequestException("role invalid");
        }
    }


    private void saveAttendance(List<AttendanceGroupRequest> attendanceGroup) {
        attendanceGroup.forEach( (AttendanceGroupRequest a) -> {
                    Attendance attendanceMember = mappingAttendance(a);
                    if (Validator.isTakeAttendance(a)) {
                        int idAttendance = a.getId();
                        attendanceMember = attendanceRepository.findAllById(idAttendance);
                        if (attendanceMember == null) {
                            throw new InvalidRequestException("User or content invalid ");
                        }
                        attendanceMember.setNote(a.getNote());
                        attendanceMember.setAttendance(a.isAttendance());
                    }
                    attendanceRepository.save(attendanceMember);
                }

        );
    }

    @NotNull
    private Attendance mappingAttendance(AttendanceGroupRequest attendanceRequest) {
        Attendance attendance = new Attendance();
        User user = userService.getUserById(attendanceRequest.getUserId());
        Content content = contentRepository.findContentById(attendanceRequest.getContentId());
        attendance.setUser(user);
        attendance.setContent(content);
        attendance.setAttendance(attendanceRequest.isAttendance());
        attendance.setNote(attendanceRequest.getNote());
        return attendance;


    }

    /**
     * Method getListAttendance get list attendance response
     *
     * @param contentId not null
     * @return Lis<AttendanceContentResponse>  Allowed null
     */
    @Override
    public List<AttendanceContentResponse> getListAttendance(int contentId) {
        List<AttendanceContentResponse> listAttendanceIsTrue = getListAttendanceByIdContent(contentId);
        List<AttendanceContentResponse> attendanceGroupResponse = getListMember(contentId);
        if (attendanceGroupResponse.isEmpty()) {
            return listAttendanceIsTrue;
        }
        attendanceGroupResponse.addAll(listAttendanceIsTrue);
        return attendanceGroupResponse;
    }

    private List<AttendanceContentResponse> getListMember(int contentId) {
        List<ClazzMember> clazzMembers = clazzMemberRepository.findAllByCapitalFalse(contentId);
        List<AttendanceContentResponse> attendanceContentResponseList = new ArrayList<>();
        clazzMembers.forEach(clazzMember -> attendanceContentResponseList.add(getAttendanceResponseMapClazzMember(clazzMember, contentId)));
        return attendanceContentResponseList;
    }

    private List<AttendanceContentResponse> getListAttendanceByIdContent(int contentId) {
        List<Attendance> attendanceList = attendanceRepository.findByContentId(contentId);
        List<AttendanceContentResponse> attendanceContentResponseList = new ArrayList<>();
        attendanceList.forEach(attendance -> attendanceContentResponseList.add(getAttendanceResponseMapAttendance(attendance)));
        return attendanceContentResponseList;
    }

    private static AttendanceContentResponse getAttendanceResponseMapClazzMember(ClazzMember clazzMember, int contentId) {
        AttendanceContentResponse attendanceContentResponse = AttendanceMapper.getAttendanceResponseMapObject(clazzMember, clazzMember.getUser());
        attendanceContentResponse.setContentId(contentId);
        attendanceContentResponse.setAttendance(Constant.ATTENDANCE_DEFAULT);
        attendanceContentResponse.setId(Constant.ATTENDANCE_ID_DEFAULT);
        return attendanceContentResponse;
    }

    private  AttendanceContentResponse getAttendanceResponseMapAttendance(Attendance attendance) {
        AttendanceContentResponse attendanceContentResponse = AttendanceMapper.getAttendanceResponseMapObject(attendance, attendance.getUser());
        attendanceContentResponse.setContentId(attendance.getContent().getId());
        attendanceContentResponse.setContentTitle(attendance.getContent().getTitle());
        return attendanceContentResponse;
    }

    private List<AttendanceContentResponse> getListAttendanceContentByClazzId(int clazzId, String title) {
        List<AttendanceContentResponse> attendanceContentResponses = new ArrayList<>();
        List<Content> contents = contentRepository.findContentByClazzHaveAttendances(clazzId);

        if (!StringUtils.isBlank(title)) {
            contents = contentRepository.findByIdClazzAndTitleHaveAttendances(clazzId, title);

        }
        for (Content content : contents) {
            List<Attendance> attendances = attendanceRepository.findByContentId(content.getId());
            List<AttendanceContentResponse> attendanceContentResponseMap = attendances.stream().map(this::getAttendanceResponseMapAttendance).collect(Collectors.toList());
            attendanceContentResponses.addAll(attendanceContentResponseMap);
        }
        return attendanceContentResponses;
    }

    private List getListAttendanceEventByClazzId(int clazzId, String title) {
        List<AttendanceEventResponse> attendanceEventResponses = new ArrayList<>();
        List<Event> events = eventRepository.findByClazzHaveAttendances(clazzId);
        if (!StringUtils.isBlank(title)) {
            events = eventRepository.findByIdClazzAndTitleHaveAttendances(clazzId, title);
        }
        for (Event event : events) {
            List<JoinEvent> attendances = joinEventRepository.findByEventId(event.getId());
            List<AttendanceEventResponse> attendanceEventResponseMap = attendances.stream().map(this::getAttendanceEventResponseMapper).collect(Collectors.toList());
            attendanceEventResponses.addAll(attendanceEventResponseMap);
        }
        return attendanceEventResponses;

    }

    private AttendanceEventResponse getAttendanceEventResponseMapper(JoinEvent joinEvent) {
        AttendanceEventResponse attendanceEventResponse = new AttendanceEventResponse();
        attendanceEventResponse.setId(joinEvent.getId());
        User member= joinEvent.getUser();
        attendanceEventResponse.setEventId(joinEvent.getEvent().getId());
        attendanceEventResponse.setAttendance(joinEvent.isAttendance());
        attendanceEventResponse.setUserId(member.getId());
        attendanceEventResponse.setUserName(member.getName());
        attendanceEventResponse.setEmail(member.getEmail());
        attendanceEventResponse.setPicture(member.getPicture());
        attendanceEventResponse.setEventTitle(joinEvent.getEvent().getTitle());
        return attendanceEventResponse;
    }

    @Override
    public List getListAttendanceByClazzId(int clazzId, String title, String type) {
        return ("CONTENT").equals(type) ? getListAttendanceContentByClazzId(clazzId, title) : getListAttendanceEventByClazzId(clazzId, title);
    }


}
