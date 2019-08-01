package com.solution.ntq.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @JsonProperty(value = "user")
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JsonProperty(value = "content")
    @ManyToOne
    @JoinColumn(name = "content_id")
    Content content;
    boolean isAttendance;

}
