package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Nam Truong
 * @since  at 8/08/2019
 * @version 1.01
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JoinEventRequest {
    @Id
    @Size(min = 0,max = 10000)
    int id;
    @JsonProperty(value = "isJoined")
    boolean isJoined;
    @JsonProperty(value = "isAttendance")
    boolean isAttendance;
    @Size(min = 0,max = 10000)
    int eventId;
    @NotNull
    String userId;
}
