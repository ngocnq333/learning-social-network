package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/08/2
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceGroupRequest {
    @Size(max = 214_748_364)
    int id;
    int contentId;
    @JsonProperty(value = "memberId")
    String userId;
    @JsonProperty(value = "isAttendance")
    boolean isAttendance;
    String note;
}
