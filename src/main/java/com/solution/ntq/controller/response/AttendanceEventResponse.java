package com.solution.ntq.controller.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Nam Truong
 * @version 1.01
 * @since at 9/08/2019
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceEventResponse {
    int id;
    int eventId;
    boolean isAttendance;
    String userName;
    String userId;
    String picture;
    String email;
}
