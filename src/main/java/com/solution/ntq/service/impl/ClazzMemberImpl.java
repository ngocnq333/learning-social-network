package com.solution.ntq.service.impl;

import com.solution.ntq.controller.response.ClazzMemberResponse;
import com.solution.ntq.repository.ClazzMemberRepository;
import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.service.base.ClazzMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClazzMemberImpl implements ClazzMemberService {
    ClazzMemberRepository memberRepository;

    /**
     * get Member by id member
     * @param idMember
     * @return
     */
    @Override
    public ClazzMemberResponse getMember(int idMember) {
        ClazzMember member = memberRepository.findById(idMember);
        return getClazzMemberResponseMapClazzMember(member);
    }

    private ClazzMemberResponse getClazzMemberResponseMapClazzMember(ClazzMember clazzMember) {
        ClazzMemberResponse clazzMemberResponse = new ClazzMemberResponse();
        clazzMemberResponse.setId(clazzMember.getId());
        clazzMemberResponse.setName(clazzMember.getUser().getName());
        clazzMemberResponse.setAvatar(clazzMember.getUser().getPicture());
        clazzMemberResponse.setEmail(clazzMember.getUser().getEmail());
        clazzMemberResponse.setSkype(clazzMember.getUser().getSkype());
        clazzMemberResponse.setJoinDate(clazzMember.getJoinDate());
        clazzMemberResponse.setCapital(clazzMember.isCapital());

        return clazzMemberResponse;
    }
}
