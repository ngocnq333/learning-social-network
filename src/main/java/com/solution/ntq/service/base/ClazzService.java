package com.solution.ntq.service.base;

import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.entities.Clazz;

import java.text.ParseException;
import java.util.List;

/**
 * @author Duc Anh
 * @version 1.01
 * @since 2019/02/08
 */
public interface ClazzService {
    void addClazz(Clazz clazz);

    List<ClazzResponse> getClassByUser(String userId);


    ClazzResponse getClassById(int clazzId);

    ClazzResponse getClassById(int clazzId, String tokenId);

    void updateCaptainForClass(int clazzId, String tokenId, String userId);

    boolean isCaptainClazz(String userId, int clazzId);

}
