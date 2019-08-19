package com.solution.ntq.service.base;

import com.solution.ntq.controller.request.ClazzRequest;
import com.solution.ntq.controller.request.MemberRequest;
import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.entities.Clazz;

import java.io.IOException;
import java.security.GeneralSecurityException;
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

    List<ClazzMemberResponse> findAllMemberByClazzId(int clazzId,String status,String idToken) throws IllegalAccessException, GeneralSecurityException, IOException;

    boolean checkUserIsCaptainOfClazz(String userId, int classId);

    ClazzMemberResponse addClazzMember(MemberRequest memberRequest, int classId, String idToken) throws IllegalAccessException, GeneralSecurityException, IOException;

    void updateCaptainForClass(int clazzId, String tokenId, String userId);

    boolean isCaptainClazz(String userId, int clazzId);

    void deleteMember(int clazzId, String idToken, String memberId) throws IllegalAccessException, GeneralSecurityException, IOException, ParseException;

    void updateClazz(String tokenId, ClazzRequest clazzRequest, int clazzId) throws GeneralSecurityException, IOException;

    ClazzMemberResponse updateStatusMember(String idToken, int classId, String memberId) throws GeneralSecurityException, IOException;
}
