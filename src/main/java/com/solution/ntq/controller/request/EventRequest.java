package com.solution.ntq.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequest {
    @Id
    int id;
    @Size(min = 1,max = 65)
    @NotEmpty(message = "Title is not blank !")
    String title;
    @NotEmpty(message = "Description is not blank !")
    String description;
    String document;
    @NotEmpty(message = "speaker is not blank !")
    String speaker;
    @NotNull(message = "Start Date is not blank !")
    Date startDate;
    @NotNull(message = "Duration is not blank !")
    @DecimalMin(value = "0.4")
    float duration;
    @NotNull
    int classId;

    int contentId;
}
