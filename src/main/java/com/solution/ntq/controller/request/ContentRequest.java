package com.solution.ntq.controller.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Duc Anh
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContentRequest {
    int id;

    int classId;
    @NotNull(message = "Please pick a start date")
    Date startDate;
    @NotNull(message = "Please pick a end date")
    Date endDate;
    @NotNull(message = "Content is required !")
    String content;
    @NotNull(message = "Title is required !")
    String title;
    @NotNull(message = "Level is required !")
    String level;

}
