package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/08/1
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest {
    @NotNull
    String userId;
    @JsonProperty(value = "classId")
    @NotNull
    int clazzId;
    @NotNull
    List<AttendanceGroupRequest> attendanceGroupRequests;
    String note;
}
