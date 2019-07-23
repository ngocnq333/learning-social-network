package com.solution.ntq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User Class provide all properties and method of entity User in Project
 * @author Ngo Quy Ngoc
 * @version 1.01
 * Created at 18/07/2019*/


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    private String id;
    private String email;
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String picture;
    private String skype;
    private String hd;
    private String locale;
    private Date dateOfBirth;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<ClassMember> classMembers;

}
