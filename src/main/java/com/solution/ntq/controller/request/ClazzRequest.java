package com.solution.ntq.controller.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzRequest {
    String name;
    String description;
    String thumbnail;
    String avatar;
}
