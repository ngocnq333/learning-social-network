package com.solution.ntq.service;

import com.solution.ntq.model.Clazz;
import com.solution.ntq.model.User;

import java.text.ParseException;
import java.util.List;
/**
 * @author Duc Anh
 */
public interface IClazzService {
    void addClazz(Clazz clazz);

    List<Clazz> getClassByUser(String userId);

    User findCapitalByClass(Clazz clazz);

    void addAllData() throws ParseException;

    Clazz getClassById(int clazzId);
}
