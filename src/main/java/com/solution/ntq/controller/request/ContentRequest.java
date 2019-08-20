package com.solution.ntq.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
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
    @NotNull
    int id;
    @NotNull
    @JsonProperty("classId")
    int clazzId;
    @NotNull(message = "Please pick a start date")
    Date startDate;
    @NotNull(message = "Please pick a end date")
    Date endDate;
    @NotNull(message = "Content is required !")
    String content;
    @NotNull(message = "Title is required !")
    @Length(max = 64 ,message = "Length must be smaller than 64")
    String title;
    @NotNull(message = "Level is required !")
    String level;

}
