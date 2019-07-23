package com.solution.ntq.service.impl;

import com.solution.ntq.model.ClassMember;
import com.solution.ntq.model.Clazz;
import com.solution.ntq.model.User;
import com.solution.ntq.repository.IClassMemberRepository;
import com.solution.ntq.repository.IClazzRepository;
import com.solution.ntq.service.IClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClazzServiceImpl implements IClazzService {
    @Autowired
    IClazzRepository iClazzRepository;
    @Autowired
    IClassMemberRepository iClassMemberRepository;

    @Override
    public void addClazz(Clazz clazz) {
        iClazzRepository.save(clazz);

    }

    @Override
    public List<Clazz> getClassByUser(String userId) {
        List<ClassMember> classMembers = iClassMemberRepository.findByUserId(userId);
        return classMembers.stream().map(i->i.getClazz()).collect(Collectors.toList());
    }

    @Override
    public User findCapitalByClass(Clazz clazz) {
        ClassMember classMember=iClassMemberRepository.findByClazzAndIsCapitalTrue(clazz);
        return null;
    }
}
