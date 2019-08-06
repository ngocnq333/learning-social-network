package com.solution.ntq.service.impl;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.validator.Validator;
import com.solution.ntq.controller.request.AttendanceGroupRequest;
import com.solution.ntq.controller.request.AttendanceRequest;
import com.solution.ntq.controller.response.Response;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.common.constant.Constant;
import com.solution.ntq.common.excaption.InvalidRequestException;
import com.solution.ntq.controller.response.AttendanceResponse;
import com.solution.ntq.repository.AttendanceRepository;
import com.solution.ntq.repository.ClazzMemberRepository;
import com.solution.ntq.repository.ContentRepository;
import com.solution.ntq.repository.entities.Attendance;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.repository.ContentRepository;
import com.solution.ntq.repository.entities.Attendance;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.AttendanceService;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.service.base.UserService;
import lombok.AccessLevel;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;


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

    private UserService userService;
    private ContentRepository contentRepository;
    private ClazzService clazzService;


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
        attendanceGroup.forEach(attendance -> {
                    if (Validator.isTakeAttendance(attendance)) {
                        int idAttendance = attendance.getId();
                        Attendance attendanceUpdate = attendanceRepository.findAllById(idAttendance);
                        if (attendanceUpdate == null) {
                            throw new InvalidRequestException("user or content invalid ");
                        }
                        attendanceUpdate.setAttendance(attendance.isAttendance());
                        attendanceRepository.save(attendanceUpdate);
                    } else if (attendance.isAttendance()) {
                        Attendance attendanceCreate = mappingAttendance(attendance.getUserId(), attendance.getContentId(), attendance.isAttendance());
                        attendanceRepository.save(attendanceCreate);
                    }
                }
        );
    }

    @NotNull
    private Attendance mappingAttendance(String userId, int contentId, boolean isAttendance) {
        Attendance attendance = new Attendance();
        User user = userService.getUserById(userId);
        Content content = contentRepository.findContentById(contentId);
        attendance.setUser(user);
        attendance.setContent(content);
        attendance.setAttendance(isAttendance);
        return attendance;
    }

    /**
     * Method getListAttendance get list attendance response
     *
     * @param contentId not null
     * @return Lis<AttendanceResponse>  Allowed null
     */
    @Override
    public List<AttendanceResponse> getListAttendance(int contentId) {
        //List attendance in database attendance
        List<AttendanceResponse> listAttendanceIsTrue = getListAttendanceByIdContent(contentId);
        // List attendance map listMember
        List<AttendanceResponse> attendanceGroupResponse = getListMember(contentId);

        if (attendanceGroupResponse.isEmpty()) {
            throw new InvalidRequestException("Invalid request");
        }
        Map<String, List<AttendanceResponse>> listMapAttendanceFalse =
                attendanceGroupResponse.stream().collect(Collectors.groupingBy(AttendanceResponse::getUserId));
        Map<String, List<AttendanceResponse>> listMapAttendanceTrue =
                listAttendanceIsTrue.stream().collect(Collectors.groupingBy(AttendanceResponse::getUserId));
        listMapAttendanceFalse.putAll(listMapAttendanceTrue);

        return listMapAttendanceFalse.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<AttendanceResponse> getListAttendanceByIdContent(int contentId) {
        List<Attendance> attendanceList = attendanceRepository.findByContentId(contentId);

        return getListAttendance(attendanceList);
    }

    private List<AttendanceResponse> getListMember(int contentId) {
        List<ClazzMember> clazzMembers = clazzMemberRepository.findAllByCapitalNot(contentId);
        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();

        for (ClazzMember clazzMember : clazzMembers) {
            attendanceResponseList.add(getAttendanceResponseMapClazzMember(clazzMember, contentId));
        }

        return attendanceResponseList;
    }

    private List<AttendanceResponse> getListAttendance(List<Attendance> attendanceList) {
        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            attendanceResponseList.add(getAttendanceResponseMapAttendance(attendance));
        }
        return attendanceResponseList;

    }

    private AttendanceResponse getAttendanceResponseMapClazzMember(ClazzMember clazzMember, int contentId) {
        AttendanceResponse attendanceResponse = getAttendanceResponseMapObject(clazzMember, clazzMember.getUser());
        attendanceResponse.setContentId(contentId);
        attendanceResponse.setAttendance(Constant.ATTENDANCE_DEFAULT);
        attendanceResponse.setId(Constant.ATTENDANCE_ID_DEFAULT);

        return attendanceResponse;
    }

    private AttendanceResponse getAttendanceResponseMapAttendance(Attendance attendance) {
        AttendanceResponse attendanceResponse = getAttendanceResponseMapObject(attendance, attendance.getUser());
        attendanceResponse.setContentId(attendance.getContent().getId());
        return attendanceResponse;
    }

    private AttendanceResponse getAttendanceResponseMapObject(Object object, User user) {
        AttendanceResponse attendanceResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        attendanceResponse = objectMapper.convertValue(object, AttendanceResponse.class);
        attendanceResponse.setUserId(user.getId());
        attendanceResponse.setUserName(user.getName());
        attendanceResponse.setPicture(user.getPicture());
        attendanceResponse.setEmail(user.getEmail());
        return attendanceResponse;
    }

}
