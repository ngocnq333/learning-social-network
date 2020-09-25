package com.solution.ntq.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Duc Anh
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "classId")
    int id;
    String name;

    String description;
    String thumbnail;
    String avatar;
    Date startDate;
    Date endDate;
    @JsonIgnore
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL)
    List<ClazzMember> clazzMembers;
    @JsonIgnore
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL)
    List<Content> contents;
}
