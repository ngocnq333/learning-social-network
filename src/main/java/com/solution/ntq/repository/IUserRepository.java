package com.solution.ntq.repository;


import com.solution.ntq.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User,String> {
    @Override
    <S extends User> S save(S s);

    @Override
    boolean existsById(String s);
}
