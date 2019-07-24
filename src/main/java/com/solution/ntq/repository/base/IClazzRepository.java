package com.solution.ntq.repository.base;

import com.solution.ntq.model.Clazz;

import org.springframework.data.repository.Repository;
/**
 * @author Duc Anh
 */

public interface IClazzRepository extends Repository<Clazz,Integer> {

    void save (Clazz clazz);
    Clazz findClazzById(int clazzId);



}
