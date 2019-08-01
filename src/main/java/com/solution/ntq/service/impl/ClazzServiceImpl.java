package com.solution.ntq.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solution.ntq.common.constant.Status;
import com.solution.ntq.controller.response.ClazzResponse;
import com.solution.ntq.repository.ContentRepository;
import com.solution.ntq.repository.TokenRepository;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.Token;
import com.solution.ntq.repository.entities.User;
import com.solution.ntq.repository.ClazzMemberRepository;
import com.solution.ntq.repository.ClazzRepository;
import com.solution.ntq.service.base.ClazzService;
import com.solution.ntq.common.validator.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Duc Anh
 */

@AllArgsConstructor
@Service
public class ClazzServiceImpl implements ClazzService {

    private ClazzRepository clazzRepository;

    private ClazzMemberRepository clazzMemberRepository;
    private ContentRepository contentRepository;
    private TokenRepository tokenRepository;

    @Override
    public void addClazz(Clazz clazz) {
        clazzRepository.save(clazz);

    }

    @Override
    public List<ClazzResponse> getClassByUser(String userId) {
        List<ClazzResponse> clazzResponses = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(userId)) {
            List<Clazz> clazzList = clazzRepository.findAll();
            for (Clazz clazz : clazzList) {
                clazzResponses.add(getResponseMapByClazz(clazz));
            }
            return clazzResponses;
        }
        List<ClazzMember> clazzMembers = clazzMemberRepository.findByUserId(userId);
        List<Clazz> clazzList = clazzMembers.stream().map(ClazzMember::getClazz).collect(Collectors.toList());
        return  clazzList.stream().map(i->getResponseMapByClazz(i)).collect(Collectors.toList());
    }



    @Override
    public void addAllData() throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.Date date = sdf.parse("2018-04-10T04:00:00.000Z");
        java.util.Date date1 = sdf.parse("2018-04-10T04:00:00.000Z");
        java.util.Date date2 = sdf.parse("2018-04-10T04:00:00.000Z");
        java.util.Date date3 = sdf.parse("2018-04-10T04:00:00.000Z");

        User user = new User();
        user.setId("a");
        user.setName("DucAnh");
        User user1 = new User();
        user1.setId("b");
        user1.setName("Manh");
        Clazz clazz = new Clazz();
        clazz.setName("Java");
        clazz.setDescription("Class about java");
        clazz.setStartDate(date);
        clazz.setEndDate(date1);
        clazz.setSlug("Slug Info");
        clazz.setThumbnail("https://undesigns.net/wp-content/uploads/2018/02/Material-Design-Background-Undesigns-00.jpg");
        Clazz clazz1 = new Clazz();
        clazz1.setName("PHP");
        clazz1.setDescription("Class about PHP");
        clazz1.setStartDate(date2);
        clazz1.setEndDate((date3));
        clazz1.setSlug("Slug Info");
        clazz1.setThumbnail("https://undesigns.net/wp-content/uploads/2018/02/Material-Design-Background-Undesigns-00.jpg");
        ClazzMember clazzMember = new ClazzMember();
        clazzMember.setClazz(clazz);
        clazzMember.setUser(user1);
        clazzMember.setStatus(Status.JOINED.value());
        ClazzMember clazzMember1 = new ClazzMember();
        clazzMember1.setClazz(clazz1);
        clazzMember1.setCaptain(true);
        clazzMember1.setUser(user1);
        clazzMember1.setStatus(Status.JOINED.value());
        ClazzMember clazzMember2 = new ClazzMember();
        clazzMember2.setClazz(clazz);
        clazzMember2.setCaptain(true);
        clazzMember2.setUser(user);
        clazzMember2.setStatus(Status.JOINED.value());
        ClazzMember clazzMember3 = new ClazzMember();
        clazzMember3.setClazz(clazz1);
        clazzMember3.setUser(user);
        clazzMember3.setStatus(Status.JOINED.value());
        List<ClazzMember> listClazzMember = new ArrayList<>();
        listClazzMember.add(clazzMember);
        listClazzMember.add(clazzMember1);
        listClazzMember.add(clazzMember2);
        listClazzMember.add(clazzMember3);
        clazz.setClazzMembers(listClazzMember);
        clazz1.setClazzMembers(listClazzMember);
        user.setClazzMembers(listClazzMember);
        user1.setClazzMembers(listClazzMember);
        addClazz(clazz1);
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
        for ( ClazzMember member :clazzRepository.findClazzById(clazzId).getClazzMembers())
        {

            if(member.getUser().getId().contains(userId))
            {
                if(member.getStatus().equals(Status.APPROVE.value()))
                {
                    clazzResponse.setStatus(Status.APPROVE.value());
                    break;
                }
                else
                {
                    clazzResponse.setStatus(Status.JOINED.value());
                    break;
                }

            }
                clazzResponse.setStatus(Status.NOTJOIN.value());
                break;

        }
        return clazzResponse;
    }

    private ClazzResponse getResponseMapByClazz(Clazz clazz) {
        ClazzResponse clazzResponse;
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        clazzResponse = mapper.convertValue(clazz, ClazzResponse.class);
        ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndIsCaptainTrue(clazzResponse.getId());
        clazzResponse.setCaptainName(clazzMember.getUser().getName());
        clazzResponse.setCaptainId(clazzMember.getUser().getId());
        clazzResponse.setMembers(clazzMemberRepository.countAllByClazzId(clazz.getId()));
        clazzResponse.setPendingItems(contentRepository.findAllByClazzIdAndIsApproveFalse(clazz.getId()).size());
        clazzResponse.setEventNumber(1);
        return clazzResponse;
    }


}
