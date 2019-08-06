package com.solution.ntq.controller.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Manh
 */

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzMemberResponse {
    @Id
    int classId;
    String userId;
    String name;
    String email;
    String avatar;
    private String skype;
    boolean isCaptain;
    Date joinDate;
    String status;
}
