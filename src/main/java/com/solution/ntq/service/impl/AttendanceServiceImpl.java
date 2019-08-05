package com.solution.ntq.service.impl;

import com.solution.ntq.common.constant.ResponseCode;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.validator.Validator;
import com.solution.ntq.controller.request.AttendanceGroupRequest;
import com.solution.ntq.controller.request.AttendanceRequest;
import com.solution.ntq.controller.response.Response;
import com.solution.ntq.repository.AttendanceRepository;
import com.solution.ntq.repository.ContentRepository;
import com.solution.ntq.repository.entities.Attendance;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.AttendanceService;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.service.base.UserService;
import lombok.AllArgsConstructor;
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
}
