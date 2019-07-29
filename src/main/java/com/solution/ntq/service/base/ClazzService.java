package com.solution.ntq.service.base;

import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.User;

import java.text.ParseException;
import java.util.List;
/**
 * @author Duc Anh
 */
public interface ClazzService {
    void addClazz(Clazz clazz);

    List<ClazzResponse> getClassByUser(String userId);

    void addAllData() throws ParseException;

    ClazzResponse getClassById(int clazzId);


}
