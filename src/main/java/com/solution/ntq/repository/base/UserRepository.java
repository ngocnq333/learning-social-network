package com.solution.ntq.repository.base;


import com.solution.ntq.repository.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nam_Phuong
 * Delear user repository
 * Date update 24/7/2019
 */

@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<User, String> {
    /**
     * create one user
     */
    void save(User user);

    /**
     * Fine user by id user
     */
    User findById(String id);

    /**
     * Check exist user by id
     */
    boolean existsById(String id);

    User findUserByTokenIdToken(String tokenId);

    @Query("FROM User WHERE UPPER(email) LIKE %?#{[0].toUpperCase()}%")
    List<User> findByEmailContains(String email);

    List<User> findAll();
}
