package com.solution.ntq.repository.base;

import com.solution.ntq.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author Duc Anh
 */
public interface ITokenRepository extends JpaRepository<Token, String> {
    @Override
    <S extends Token> S save(S s);

    @Override
    Token getOne(String s);

    @Override
    void delete(Token token);
}
