package com.solution.ntq.controller.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceResponse {
    String userName;
    String userId;
    String picture;
    String email;
    int contentId;
    int id;
    boolean isAttendance;
}
