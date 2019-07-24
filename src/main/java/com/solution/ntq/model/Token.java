package com.solution.ntq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
/**
 * @author Duc Anh
 */
public class Token {
    @Id
    String userId;
    String refreshToken;
}
