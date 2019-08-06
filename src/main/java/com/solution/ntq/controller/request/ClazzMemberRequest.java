package com.solution.ntq.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzMemberRequest {
    String userId;
    @Id
    String userIdAdd;
    String email;
    String name;
    String givenName;
    String familyName;
    String picture;
    String skype;
    Date dateOfBirth;
    String status;
}
