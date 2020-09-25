package com.solution.ntq.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Content;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzResponse {

    @JsonProperty(value = "classId")
    int id;
    String name;
    String description;
    String thumbnail;
    Date startDate;
    Date endDate;
    String avatar;
    @JsonIgnore
    List<ClazzMember> clazzMember= new ArrayList<>();
    @JsonIgnore
    List<Content> contents= new ArrayList<>();
    int members;
    String captainId;
    String captainName;
    int pendingItems;
    int eventNumber;
    String status;
}
