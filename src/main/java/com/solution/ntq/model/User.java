package com.solution.ntq.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", verified_email=" + verified_email +
                ", name='" + name + '\'' +
                ", given_name='" + given_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", link='" + link + '\'' +
                ", picture='" + picture + '\'' +
                ", skype='" + skype + '\'' +
                ", hd='" + hd + '\'' +
                ", locale='" + locale + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
