package com.solution.ntq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Duc Anh
 */

public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String slug;
    String description;
    String thumbnail;
    Date startDate;
    Date endDate;
    @JsonIgnore
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL)
    List<ClassMember> classMembers;
    @JsonIgnore
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL)
    List<Content> contents;





}
