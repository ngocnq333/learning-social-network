package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.Clazz;

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

}
