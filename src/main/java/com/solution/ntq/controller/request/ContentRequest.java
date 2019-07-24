package com.solution.ntq.controller.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
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
    Date startDate;
    Date endDate;
    String content;
    String title;
    String level;

}
