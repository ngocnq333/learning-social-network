package com.solution.ntq.controller.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Manh
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzMemberResponse {
    int id;
    String name;
    String email;
    String avatar;
    private String skype;
    boolean isCapital;
    Date joinDate;
}
