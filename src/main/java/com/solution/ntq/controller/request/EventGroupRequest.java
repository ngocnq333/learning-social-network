package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Nam Truong
 * @version 1.01
 * @since 09/09/2019
 */

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventGroupRequest {
    @NotNull
    @Size
    int id;
    @JsonProperty(value = "isAttendance")
    boolean isAttendance;
    @NotNull
    int eventId;
    @NotNull
    @JsonProperty(value = "memberId")
    String userId;
    String note;
}
