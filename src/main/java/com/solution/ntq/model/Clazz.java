package com.solution.ntq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor


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
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL)
    List<ClassMember>  listMember= new ArrayList<>();

}
