package com.solution.ntq.repository.base;



import com.solution.ntq.repository.entities.Token;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Duc Anh
 */
@Repository
public interface TokenRepository extends org.springframework.data.repository.Repository<Token, String> {

    void save(Token token);

    Token findTokenByUserId(String idUser);

    @Transactional
    void removeTokenById(Long id);

}
