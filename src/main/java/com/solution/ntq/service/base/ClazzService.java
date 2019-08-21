package com.solution.ntq.service.base;

import com.solution.ntq.controller.request.ClazzRequest;
import com.solution.ntq.controller.request.MemberRequest;
import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.controller.response.ClazzResponse;

import java.util.List;
/**
 * @author Duc Anh
 * @version 1.01
 * @since 2019/02/08
 */
public interface ClazzService {

    List<ClazzResponse> getClazzByUser(String userId);

    ClazzResponse getclazzById(int clazzId);

    ClazzResponse getClazzById(int clazzId, String userId);

    List<ClazzMemberResponse> findAllMemberByClazzId(int clazzId,String status);

    ClazzMemberResponse addClazzMember(MemberRequest memberRequest, int clazzId, String userId);

    void updateCaptainForClazz(int clazzId, String userId, String userIdUpdate);

    boolean isCaptainClazz(String userId, int clazzId);

    void deleteMember(int clazzId, String userId, String memberId) ;

    void updateClazz(String userId, ClazzRequest clazzRequest, int clazzId) ;

    ClazzMemberResponse updateStatusMember(String currentUser, int clazzId, String memberId);
}
