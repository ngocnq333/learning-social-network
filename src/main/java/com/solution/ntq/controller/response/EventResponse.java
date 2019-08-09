package com.solution.ntq.controller.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author Nam Truong
 * @version 1.01
 * @since at 7/08/2019
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventResponse {
    Date startDate;
    String title;
    float duration;
    String speaker;
    String description;
    String documents;
}
