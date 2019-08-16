package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Duc Anh
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzMemberRequest {
    int id;
    @JsonProperty(value = "classId")
    int clazzId;
}
