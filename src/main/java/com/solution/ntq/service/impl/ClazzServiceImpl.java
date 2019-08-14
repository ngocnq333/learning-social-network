package com.solution.ntq.service.impl;

import com.solution.ntq.common.constant.Status;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.common.utils.GoogleUtils;
import com.solution.ntq.controller.request.ClazzRequest;
import com.solution.ntq.controller.request.MemberRequest;
import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.base.*;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.ClazzService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Duc Anh
 */

@AllArgsConstructor
@Service
public class ClazzServiceImpl implements ClazzService {

    private static final int FIRST_INDEX_OF_LIST = 0;
    private ClazzRepository clazzRepository;
    private UserRepository userRepository;
    private ClazzMemberRepository clazzMemberRepository;
    private ContentRepository contentRepository;
    private TokenRepository tokenRepository;
    private GoogleUtils googleUtils;

    @Override
    public void addClazz(Clazz clazz) {
        clazzRepository.save(clazz);

    }

    @Override
    public boolean isCaptainClazz(String userId, int clazzId) {
        if (StringUtils.isBlank(userId)) {
            return false;
        }
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId);
        return (clazzMember != null && userId.equals(clazzMember.getUser().getId()));
    }

    @Override
    public List<ClazzResponse> getClassByUser(String userId) {
        List<ClazzResponse> clazzResponses = new ArrayList<>();
        if (StringUtils.isBlank(userId)) {
            List<Clazz> clazzList = clazzRepository.findAll();
            for (Clazz clazz : clazzList) {
                clazzResponses.add(getResponseMapByClazz(clazz));
            }
            return clazzResponses;
        }
        List<ClazzMember> clazzMembers = clazzMemberRepository.findByUserId(userId);
        List<Clazz> clazzList = clazzMembers.stream().map(ClazzMember::getClazz).collect(Collectors.toList());
        return clazzList.stream().map(this::getResponseMapByClazz).collect(Collectors.toList());
    }

    @Override
    public ClazzResponse getClassById(int clazzId) {
        Clazz clazz = clazzRepository.findClazzById(clazzId);
        return getResponseMapByClazz(clazz);
    }

    @Override
    public ClazzResponse getClassById(int clazzId, String tokenId) {
        Token token = tokenRepository.findTokenByIdToken(tokenId);
        String userId = token.getUser().getId();
        ClazzResponse clazzResponse = getClassById(clazzId);
        ClazzMember memberInClass = clazzMemberRepository.findByClazzIdAndUserId(clazzId, userId);
        if (memberInClass == null) {
            clazzResponse.setStatus(Status.NOTJOIN.value());
        }
        return clazzResponse;
    }

    @Override
    public void updateCaptainForClass(int clazzId, String tokenId, String userId) {
        Token token = tokenRepository.findTokenByIdToken(tokenId);
        String userIdCurrent = token.getUser().getId();
        if (clazzRepository.findClazzById(clazzId) == null) {
            throw new InvalidRequestException("Not have this class in system !");
        }
        ClazzMember captainMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId);
        String captainMemberId = captainMember.getUser().getId();
        if (!captainMember.getUser().getId().equals(userIdCurrent)) {
            throw new InvalidRequestException("Current user not is captain of class !");
        }
        ClazzMember memberInClass = clazzMemberRepository.findByClazzIdAndUserId(clazzId, userId);
        // check member in class or not
        if (memberInClass == null || (userRepository.findById(userId).getId().equals(captainMemberId))) {
            throw new InvalidRequestException("Not find user in class or invalid user !");
        }

        memberInClass.setCaptain(true);
        captainMember.setCaptain(false);
        clazzMemberRepository.save(memberInClass);
        clazzMemberRepository.save(captainMember);

    }

    private ClazzResponse getResponseMapByClazz(Clazz clazz) {
        ClazzResponse clazzResponse;
        clazzResponse = ConvertObject.mapper().convertValue(clazz, ClazzResponse.class);
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzResponse.getId());
        clazzResponse.setCaptainName(clazzMember.getUser().getName());
        clazzResponse.setCaptainId(clazzMember.getUser().getId());
        clazzResponse.setMembers(clazzMemberRepository.countAllByClazzId(clazz.getId()));
        clazzResponse.setPendingItems(contentRepository.findAllByClazzIdAndIsApproveFalse(clazz.getId()).size());
        clazzResponse.setEventNumber(1);
        return clazzResponse;
    }

    public List<ClazzMemberResponse> findAllMemberByClazzId(int clazzId, String status, String idToken) throws IOException, GeneralSecurityException {
        validatorParamsAddMember(clazzId, idToken);
        List<ClazzMemberResponse> listResponse = new ArrayList<>();
        if (Status.APPROVE.value().equals(status)) {
            List<ClazzMember> memberPending = clazzMemberRepository.findByClazzIdAndIsCaptainIsNot(clazzId, Status.APPROVE.value());
            if (memberPending == null || memberPending.isEmpty()) {
                return listResponse;
            }
            listResponse = memberPending.stream().map(this::convertToResponse).collect(Collectors.toList());
            return listResponse;
        }
        List<ClazzMember> listClazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainIsNot(clazzId, Status.JOINED.value());
        ClazzMemberResponse clazzMemberCaptain = convertToResponse(clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId));
        if (listClazzMember == null || listClazzMember.isEmpty()) {
            listResponse.add(FIRST_INDEX_OF_LIST, clazzMemberCaptain);
            return listResponse;
        }

        listResponse = listClazzMember.stream().map(this::convertToResponse).collect(Collectors.toList());
        listResponse.add(FIRST_INDEX_OF_LIST, clazzMemberCaptain);
        return listResponse;
    }

    private ClazzMemberResponse convertToResponse(ClazzMember clazzMember) {

        ClazzMemberResponse response = ConvertObject.mapper().convertValue(clazzMember, ClazzMemberResponse.class);
        response.setUserId(clazzMember.getUser().getId());
        response.setName(clazzMember.getUser().getName());
        response.setEmail(clazzMember.getUser().getEmail());
        response.setAvatar(clazzMember.getUser().getPicture());
        response.setSkype(clazzMember.getUser().getSkype());
        response.setJoinDate(clazzMember.getJoinDate());
        return response;
    }


    private void validatorParamsAddMember(int classId, String idToken) throws IOException, GeneralSecurityException {
        String userId = GoogleUtils.getUserIdByIdToken(idToken);
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new InvalidRequestException("IdToken  illegal !");
        }
        Clazz clazz = clazzRepository.findClazzById(classId);
        if (clazz == null) {
            throw new InvalidRequestException("Class Id illegal !");
        }
    }

    private boolean checkUserIsCaptain(String userId, int clazzId) {
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId);
        return !userId.equals(clazzMember.getUser().getId());
    }

    @Override
    public boolean checkUserIsCaptainOfClazz(String userId, int classId) {
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(classId);
        return clazzMember.getUser().getId().equals(userId);
    }

    @Override
    public ClazzMemberResponse addClazzMember(MemberRequest memberRequest, int classId, String idToken) throws IllegalAccessException, GeneralSecurityException, IOException {
        validatorParamsAddMember(classId, idToken);
        String userCaptainId = GoogleUtils.getUserIdByIdToken(idToken);
        if (checkUserIsCaptain(userCaptainId, classId)) {
            throw new IllegalAccessException("User not is caption of class not enough permission");
        }
        User userAdd = userRepository.findById(memberRequest.getUserIdAdd());
        if (userAdd == null) {
            throw new InvalidRequestException("User ID illegal !");
        }
        ClazzMember newClazzMember = new ClazzMember();
        User user = userRepository.findById(memberRequest.getUserIdAdd());
        newClazzMember.setUser(user);
        String status = checkUserIsCaptain(userCaptainId, classId) ? Status.JOINED.value() : Status.APPROVE.value();
        newClazzMember.setStatus(status);
        newClazzMember.setJoinDate(new Date());
        newClazzMember.setClazz(clazzRepository.findClazzById(classId));
        newClazzMember.setCaptain(false);
        return convertToResponse(clazzMemberRepository.save(newClazzMember));

    }

    @Override
    public void deleteMember(int clazzId, String idToken, String userIdDelete) throws IllegalAccessException, GeneralSecurityException, IOException {
        validatorParamsAddMember(clazzId, idToken);
        if (checkUserIsCaptain(GoogleUtils.getUserIdByIdToken(idToken), clazzId)) {
            throw new IllegalAccessException("User not is caption of class not enough permission");
        }
        User user = userRepository.findById(userIdDelete);
        if (user == null) {
            throw new IllegalAccessException("User invalid !");
        }
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndUserId(clazzId, user.getId());
        if (clazzMember == null || !clazzMember.getStatus().equals(Status.JOINED.value())) {
            throw new IllegalAccessException("user not joined in class !");
        }
        clazzMemberRepository.deleteById(clazzMember.getId());
    }

    /**
     *Update information of Clazz
     */
    @Override
    public void updateClazz(String tokenId, ClazzRequest clazzRequest, int classId) throws GeneralSecurityException, IOException {
            String userId = GoogleUtils.getUserIdByIdToken(tokenId);
            if (!isCaptainClazz(userId, classId)) {
                throw new InvalidRequestException("User is not captain !");
            }
            Clazz clazzOld = clazzRepository.findClazzById(classId);
            clazzRepository.save(getClazzMapToClazzRequest(clazzRequest, clazzOld));
    }

    private Clazz getClazzMapToClazzRequest(ClazzRequest clazzRequest, Clazz clazzOld) {
        Clazz newClazz = ConvertObject.mapper().convertValue(clazzRequest, Clazz.class);
        newClazz.setId(clazzOld.getId());
        newClazz.setStartDate(clazzOld.getStartDate());
        newClazz.setEndDate(clazzOld.getEndDate());
        newClazz.setContents(clazzOld.getContents());
        newClazz.setClazzMembers(clazzOld.getClazzMembers());
        return newClazz;
    }

}
