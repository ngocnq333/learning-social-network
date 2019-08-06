package com.solution.ntq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.common.constant.Status;
import com.solution.ntq.common.utils.ConvertObject;
import com.solution.ntq.controller.request.MemberRequest;
import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.common.exception.InvalidRequestException;
import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.*;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.service.base.ClazzService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static final int FIRST_INDEX_OF_LIST = 0;
    private ClazzRepository clazzRepository;
    private UserRepository userRepository;
    private ClazzMemberRepository clazzMemberRepository;
    private ContentRepository contentRepository;
    private TokenRepository tokenRepository;

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
        return clazzList.stream().map(i -> getResponseMapByClazz(i)).collect(Collectors.toList());
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
        ClazzResponse clazzResponse =getClassById(clazzId);
        ClazzMember memberInClass = clazzMemberRepository.findByClazzIdAndUserId(clazzId,userId);
        if (memberInClass ==null) {
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
       ClazzMember memberInClass = clazzMemberRepository.findByClazzIdAndUserId(clazzId,userId);
        // check member in class or not
        if (memberInClass==null || (userRepository.findById(userId).getId().equals(captainMemberId))) {
            throw new InvalidRequestException("Not find user in class or invalid user !");
        }

        memberInClass.setCaptain(true);
        captainMember.setCaptain(false);
        clazzMemberRepository.save(memberInClass);
        clazzMemberRepository.save(captainMember);

    }

    private ClazzResponse getResponseMapByClazz(Clazz clazz) {
        ClazzResponse clazzResponse;
        ObjectMapper mapper = ConvertObject.mapper();
        clazzResponse = mapper.convertValue(clazz, ClazzResponse.class);
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainIsTrue(clazzResponse.getId());
        clazzResponse.setCaptainName(clazzMember.getUser().getName());
        clazzResponse.setCaptainId(clazzMember.getUser().getId());
        clazzResponse.setMembers(clazzMemberRepository.countAllByClazzId(clazz.getId()));
        clazzResponse.setPendingItems(contentRepository.findAllByClazzIdAndIsApproveFalse(clazz.getId()).size());
        clazzResponse.setEventNumber(1);
        return clazzResponse;
    }
    public List<ClazzMemberResponse> findAllMemberByClazzId(int clazzId) {
        List<ClazzMember> listClazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainIsNot(clazzId);
        List<ClazzMemberResponse> listResponse = new ArrayList<>();
        if (listClazzMember == null || listClazzMember.isEmpty()){
            return listResponse;
        }

        listClazzMember.forEach( clazzMember -> listResponse.add(convertToResponse(clazzMember)));
        ClazzMemberResponse clazzMemberCaptain = convertToResponse(clazzMemberRepository.findByClazzIdAndIsCaptainIsTrue(clazzId));
        listResponse.add(FIRST_INDEX_OF_LIST,clazzMemberCaptain);
        return listResponse;
    }


    private ClazzMemberResponse convertToResponse(ClazzMember clazzMember){
        ObjectMapper mapper = ConvertObject.mapper();
        ClazzMemberResponse response = mapper.convertValue(clazzMember,ClazzMemberResponse.class);
        response.setUserId(clazzMember.getUser().getId());
        response.setName(clazzMember.getUser().getName());
        response.setEmail(clazzMember.getUser().getEmail());
        response.setAvatar(clazzMember.getUser().getPicture());
        response.setSkype(clazzMember.getUser().getSkype());
        response.setJoinDate(clazzMember.getJoinDate());
        return response;
    }



    private boolean isIllegalParamsAddMember(String userId, String userIdAdd, int classId) {
        boolean checkUserIdNull = StringUtils.isBlank(userId);
        boolean checkUserIAdddNull = StringUtils.isBlank(userId);
        if (checkUserIAdddNull || checkUserIdNull){
            return true;
        }

        User checkUserExist = userRepository.findById(userId);
        User checkUserAddExist = userRepository.findById(userIdAdd);

        ClazzMember clazzMemberContainUserIdAdd = clazzMemberRepository.findByClazzIdAndUserId(classId,userIdAdd);


        Clazz checkClassExist = clazzRepository.findClazzById(classId);


        return (  checkUserExist == null || checkUserAddExist == null || checkClassExist == null || clazzMemberContainUserIdAdd != null);
    }

    @Override
    public boolean checkUserIsCaptainOfClazz(String userId, int classId) {
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainIsTrue(classId);
        return clazzMember.getUser().getId().equals(userId);
    }

    @Override
    public ClazzMemberResponse addClazzMember(MemberRequest memberRequest, int classId) {
        String[] status = {"joined","pending","has left"};
        if (isIllegalParamsAddMember(memberRequest.getUserId(), memberRequest.getUserIdAdd(),classId)){
            return null;
        }
        ClazzMember newclazzMember = new ClazzMember();
        User user = userRepository.findById(memberRequest.getUserIdAdd());
        newclazzMember.setUser(user);

        if (checkUserIsCaptainOfClazz(memberRequest.getUserId(),classId)){
            newclazzMember.setStatus(status[0]);
        }
        else newclazzMember.setStatus(status[1]);

        newclazzMember.setJoinDate(new Date());

        newclazzMember.setClazz(clazzRepository.findClazzById(classId));
        newclazzMember.setCaptain(false);
        return convertToResponse(clazzMemberRepository.save(newclazzMember));

    }
}
