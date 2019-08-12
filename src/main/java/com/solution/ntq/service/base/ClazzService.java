package com.solution.ntq.service.base;

import com.solution.ntq.controller.request.MemberRequest;
import com.solution.ntq.controller.response.ClazzMemberResponse;
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

    ClazzResponse getClassById(int clazzId,String tokenId);

    List<ClazzMemberResponse> findAllMemberByClazzId(int clazzId);

    boolean checkUserIsCaptainOfClazz(String userId, int classId);

    ClazzMemberResponse addClazzMember(MemberRequest memberRequest, int classId);

    void updateCaptainForClass(int clazzId, String tokenId, String userId);

    boolean isCaptainClazz(String userId, int clazzId);

    void deleteMember(int clazzId, String idToken, String userId) throws IllegalAccessException;
}