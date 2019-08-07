package com.solution.ntq.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User Class provide all properties and method of entity User in Project
 *
 * @author Ngo Quy Ngoc
 * @version 1.01
 * Created at 18/07/2019
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    boolean verifiedEmail;
    String name;
    String givenName;
    String familyName;
    String link;
    String picture;
    String skype;
    String hd;
    String locale;
    Date dateOfBirth;
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    Token token;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<ClazzMember> clazzMembers;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Attendance> attendances;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<JoinEvent> joinEvents;

}
