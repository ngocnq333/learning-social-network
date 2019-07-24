package com.solution.ntq.repository.base;
import com.solution.ntq.model.User;
import org.springframework.stereotype.Repository;
/**
 * @author Duc Anh
 */
@Repository
public interface IUserRepository extends org.springframework.data.repository.Repository<User, String> {
    void save(User user);

    User findById(String id );

    boolean existsById(String id);


}
