package com.solution.ntq.repository;


import com.solution.ntq.model.ClassMember;
import com.solution.ntq.model.Clazz;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface IClazzRepository extends Repository<Clazz,Integer> {

    void save (Clazz clazz);
    Clazz findById(int id);


}
