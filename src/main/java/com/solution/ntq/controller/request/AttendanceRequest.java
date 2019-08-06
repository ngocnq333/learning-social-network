package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/08/1
 */
@Getter
@Setter
public class AttendanceRequest {
    @NotNull
    private String userId;
    @JsonProperty(value = "classId")
    @NotNull
    private int clazzId;
    @NotNull
    private List<AttendanceGroupRequest> attendanceGroupRequests;
}
