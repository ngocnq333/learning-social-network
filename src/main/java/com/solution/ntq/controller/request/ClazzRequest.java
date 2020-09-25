package com.solution.ntq.controller.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzRequest {
    @NotBlank(message = "Name is require !")
    String name;
    @NotBlank(message = "Description is require !")
    String description;
    String thumbnail;
    String avatar;
}
