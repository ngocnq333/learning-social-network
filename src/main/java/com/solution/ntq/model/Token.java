package com.solution.ntq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String accessToken;
    private String refreshToken;
    @Type(type = "text")
    private String idToken;
    private Date time;
    @OneToOne (fetch = FetchType.EAGER )
    @JoinColumn (name ="user_id")
    private User user;

}

