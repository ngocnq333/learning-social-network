package com.solution.ntq.repository.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Ngoc Ngo Quy
 * @since  at 7/08/2019
 * @version 1.01
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "eventId")
    int id;
    String title;
    String description;
    String documents;
    String speaker;
    Date startDate;
    float duration;
    String author;
    @ManyToOne
    @JoinColumn(name = "clazz_id")
    Clazz clazz;
    @ManyToOne
    @JoinColumn(name = "content_id")
    Content content;
    @OneToMany(mappedBy = "event")
    List<JoinEvent> joinEvents;
}

