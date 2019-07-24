package com.solution.ntq.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * @author Duc Anh
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    String userId;
    String refreshToken;
}
