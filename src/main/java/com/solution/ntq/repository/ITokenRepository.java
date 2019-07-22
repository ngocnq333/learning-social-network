package com.solution.ntq.repository;

import com.solution.ntq.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenRepository extends JpaRepository<Token, String> {
    @Override
    <S extends Token> S save(S s);

    @Override
    Token getOne(String s);

    @Override
    void delete(Token token);
}
