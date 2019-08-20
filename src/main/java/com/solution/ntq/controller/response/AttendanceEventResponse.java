package com.solution.ntq.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Nam Truong
 * @version 1.01
 * @since at 9/08/2019
 */
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceEventResponse {
    int id;
    int eventId;
    @JsonProperty(value = "isAttendance")
    boolean isAttendance;
    String userName;
    String userId;
    String picture;
    String email;
    String eventTitle;
    String note;
}
