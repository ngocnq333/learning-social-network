package com.solution.ntq.service;

import com.solution.ntq.model.Clazz;
import com.solution.ntq.model.User;

import java.util.List;

public interface IClazzService {
    void addClazz(Clazz clazz);
    List<Clazz> getClassByUser(String userId);
    User findCapitalByClass(Clazz clazz);
}
