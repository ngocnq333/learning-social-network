package com.solution.ntq.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

/**
 * UserModel Class provide all properties and method of entity User in Project
 * @author Ngo Quy Ngoc
 * @version 1.01
 * Created at 18/07/2019
 */
@Entity
@Table(name = "user")
@Setter
@lombok.Getter
@NoArgsConstructor
@RequiredArgsConstructor

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String  id; @NonNull
    private String email; @NonNull
    private boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String picture;
    private String _hashed_password;
    private String skype;
    private Date dateOfBirth;

}
