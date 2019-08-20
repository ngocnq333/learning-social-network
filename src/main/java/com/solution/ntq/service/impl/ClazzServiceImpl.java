package com.solution.ntq.service.impl;

import com.solution.ntq.common.constant.Status;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.controller.request.ClazzRequest;
import com.solution.ntq.controller.request.MemberRequest;
import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.base.*;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.ClazzService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Duc Anh
 * @version 1.01
 * @since 16/Aug/2019
 */

@AllArgsConstructor
@Service
public class ClazzServiceImpl implements ClazzService {
    private static final int FIRST_INDEX_OF_LIST = 0;
    private ClazzRepository clazzRepository;
    private UserRepository userRepository;
    private ClazzMemberRepository clazzMemberRepository;
    private ContentRepository contentRepository;
    private EventRepository eventRepository;

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
    public ClazzResponse getClassById(int clazzId, String userId) {
        ClazzResponse clazzResponse = getClassById(clazzId);
        ClazzMember memberInClass = clazzMemberRepository.findByClazzIdAndUserId(clazzId, userId);
        if (memberInClass == null) {
            clazzResponse.setStatus(Status.NOTJOIN.value());
        }
        return clazzResponse;
    }

    @Override
    public void updateCaptainForClass(int clazzId, String userIdCurrent, String userId) {
        validatorParamsAddMember(clazzId);
        ClazzMember captainMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId);
        String captainMemberId = captainMember.getUser().getId();
        if (!captainMember.getUser().getId().equals(userIdCurrent)) {
            throw new InvalidRequestException("Current user not is captain of class !");
        }
        ClazzMember memberInClass = clazzMemberRepository.findByClazzIdAndUserId(clazzId, userId);
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
        clazzResponse.setPendingItems(contentRepository.findAllByClazzIdAndIsApproveFalse(clazz.getId()).size()+clazzMemberRepository.countPedingMemberOfClazz(clazz.getId()));
        clazzResponse.setEventNumber(eventRepository.countAllByClazzId(clazz.getId()));
        return clazzResponse;
    }

    public List<ClazzMemberResponse> findAllMemberByClazzId(int clazzId, String status) {
        validatorParamsAddMember(clazzId);
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


    private void validatorParamsAddMember(int classId) {
        Clazz clazz = clazzRepository.findClazzById(classId);
        if (clazz == null) {
            throw new InvalidRequestException("Class Id illegal !");
        }
    }

    private boolean checkUserIsCaptain(String userId, int clazzId) {
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzId);
        return userId.equals(clazzMember.getUser().getId());
    }

    @Override
    public ClazzMemberResponse addClazzMember(MemberRequest memberRequest, int classId, String userCaptainId){
        validatorParamsAddMember(classId);
        User userAdd = userRepository.findById(memberRequest.getUserIdAdd());
        if (userAdd == null) {
            throw new InvalidRequestException("User ID illegal !");
        }
        List<ClazzMember> duplicateMembers = clazzMemberRepository.findByUserIdAndClazzId(userAdd.getId(), classId);
        if (!CollectionUtils.isEmpty(duplicateMembers)) {
            throw new InvalidRequestException(" Duplicate member !");
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
    public void deleteMember(int clazzId, String userId, String memberId) throws IllegalAccessException{
        validatorParamsAddMember(clazzId);
        if (!checkUserIsCaptain(userId, clazzId)) {
            throw new IllegalAccessException("User not is caption of class not enough permission");
        }
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndUserId(clazzId, memberId);
        if (clazzMember == null) {
            throw new IllegalAccessException("user not joined in class !");
        }
        clazzMemberRepository.deleteById(clazzMember.getId());
    }

    /**
     * Update information of Clazz
     */
    @Override
    public void updateClazz(String userId, ClazzRequest clazzRequest, int classId){
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

    @Override
    public ClazzMemberResponse updateStatusMember(String idCurrentUser, int classId, String memberId){
        validatorParamsAddMember(classId);
        if (!checkUserIsCaptain(idCurrentUser, classId)) {
            throw new InvalidRequestException(" User not Captain !");
        }
        ClazzMember currentMember = clazzMemberRepository.findByClazzIdAndUserId(classId, memberId);
        if (currentMember == null) {
            throw new InvalidRequestException(" Member not exist !");
        }
        currentMember.setStatus(Status.JOINED.value());
        return convertToResponse(clazzMemberRepository.save(currentMember));
    }
}
