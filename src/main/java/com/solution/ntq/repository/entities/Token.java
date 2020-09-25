package com.solution.ntq.repository.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String accessToken;
    String refreshToken;
    @Type(type = "text")
    String idToken;
    Date time;
    @OneToOne (fetch = FetchType.EAGER )
    @JoinColumn (name ="user_id")
    User user;
}

