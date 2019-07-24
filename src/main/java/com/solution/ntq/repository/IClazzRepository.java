package com.solution.ntq.repository;

import com.solution.ntq.model.Clazz;

import org.springframework.data.repository.Repository;


public interface IClazzRepository extends Repository<Clazz,Integer> {

    void save (Clazz clazz);
    Clazz findClazzById(int clazzId);



}
