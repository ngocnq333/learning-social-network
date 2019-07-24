package com.solution.ntq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    Clazz clazz;
    Date startDate;
    Date endDate;
    String authorId;
    String tag;
    boolean isApprove;
    boolean isDone;
    String level;
    String thumbnail;
    Date timeUpdate;
}
