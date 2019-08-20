package com.solution.ntq.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceContentResponse {
    String userName;
    String userId;
    String picture;
    String email;
    int contentId;
    int id;
    @JsonProperty(value = "isAttendance")
    boolean isAttendance;
    String contentTitle;
    String note;

}
