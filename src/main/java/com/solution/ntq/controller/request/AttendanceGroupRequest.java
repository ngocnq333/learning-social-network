package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/08/2
 */
@Getter
@Setter
public class AttendanceGroupRequest {
    @Size(max = 214748364)
    private int id;
    private int contentId;
    @JsonProperty(value = "memberId")
    private String userId;
    @JsonProperty(value = "isAttendance")
    private boolean isAttendance;
    private String note;
}
