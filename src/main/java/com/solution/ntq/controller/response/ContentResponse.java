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
public class ContentResponse {
    int id;
    int clazzId;
    Date startDate;

    Date endDate;
    String authorId;
    String authorName;
    String content;
    String title;
    boolean isApprove;
    boolean isDone;
    String level;
    String thumbnail;
    Date timePost;



}
