package com.solution.ntq.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
/**
 * @author Duc Anh
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClazzMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @JsonProperty(value = "class")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clazz_id")
    Clazz clazz;
    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
    @JsonProperty("isCaptain")
    boolean isCaptain;
    Date joinDate;
    String status;


}
