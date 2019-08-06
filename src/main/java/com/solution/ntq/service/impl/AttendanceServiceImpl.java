package com.solution.ntq.service.impl;

import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.AttendanceMapper;
import com.solution.ntq.common.validator.Validator;
import com.solution.ntq.controller.request.AttendanceGroupRequest;
import com.solution.ntq.controller.request.AttendanceRequest;
import com.solution.ntq.common.constant.Constant;
import com.solution.ntq.controller.response.AttendanceResponse;
import com.solution.ntq.repository.AttendanceRepository;
import com.solution.ntq.repository.ClazzMemberRepository;
import com.solution.ntq.repository.ContentRepository;
import com.solution.ntq.repository.entities.Attendance;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.service.base.AttendanceService;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
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
        List<AttendanceResponse> listAttendanceIsTrue = getListAttendanceByIdContent(contentId);
        List<AttendanceResponse> attendanceGroupResponse = getListMember(contentId);
        if (attendanceGroupResponse.isEmpty()) {
            return listAttendanceIsTrue;
        }
        attendanceGroupResponse.addAll(listAttendanceIsTrue);
        return attendanceGroupResponse;
    }

    private List<AttendanceResponse> getListMember(int contentId) {
        List<ClazzMember> clazzMembers = clazzMemberRepository.findAllByCapitalFalse(contentId);
        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
        clazzMembers.forEach(clazzMember -> attendanceResponseList.add(getAttendanceResponseMapClazzMember(clazzMember, contentId)));
        return attendanceResponseList;
    }

    private List<AttendanceResponse> getListAttendanceByIdContent(int contentId) {
        List<Attendance> attendanceList = attendanceRepository.findByContentId(contentId);
        List<AttendanceResponse> attendanceResponseList = new ArrayList<>();
        attendanceList.forEach(attendance -> attendanceResponseList.add(getAttendanceResponseMapAttendance(attendance)));
        return attendanceResponseList;
    }

    private AttendanceResponse getAttendanceResponseMapClazzMember(ClazzMember clazzMember, int contentId) {
        AttendanceResponse attendanceResponse = AttendanceMapper.getAttendanceResponseMapObject(clazzMember, clazzMember.getUser());
        attendanceResponse.setContentId(contentId);
        attendanceResponse.setAttendance(Constant.ATTENDANCE_DEFAULT);
        attendanceResponse.setId(Constant.ATTENDANCE_ID_DEFAULT);
        return attendanceResponse;
    }

    private AttendanceResponse getAttendanceResponseMapAttendance(Attendance attendance) {
        AttendanceResponse attendanceResponse = AttendanceMapper.getAttendanceResponseMapObject(attendance, attendance.getUser());
        attendanceResponse.setContentId(attendance.getContent().getId());
        return attendanceResponse;
    }
}
