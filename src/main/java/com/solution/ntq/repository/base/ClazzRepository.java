package com.solution.ntq.repository.base;

import com.solution.ntq.repository.entities.Clazz;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Duc Anh
 */

@org.springframework.stereotype.Repository
public interface ClazzRepository extends Repository<Clazz,Integer> {
    void save(Clazz clazz);
    Clazz findClazzById(int clazzId);
    List<Clazz> findAll();
    @Query(value = "SELECT * FROM clazz where id = (SELECT clazz_id FROM event where id = ?1)",nativeQuery = true)
    Clazz findClazzByEventId(int eventId);

}
