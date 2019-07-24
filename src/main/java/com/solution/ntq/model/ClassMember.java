package com.solution.ntq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Duc Anh
 */
public class ClassMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    Clazz clazz;
    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
    boolean isCapital;
    String status;


}
