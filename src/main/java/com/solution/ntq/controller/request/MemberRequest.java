package com.solution.ntq.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberRequest {
    @NotEmpty(message = "User ID not empty")
    String userIdAdd;
    @NotEmpty(message = "Email not empty")
    String email;
    @NotEmpty(message = "Name not empty")
    String name;
    String givenName;
    @NotEmpty(message = "Family Name not empty")
    String familyName;
    String picture;
    String skype;
    @NotNull(message = "Birthday not empty")
    Date dateOfBirth;
    String status;
}
