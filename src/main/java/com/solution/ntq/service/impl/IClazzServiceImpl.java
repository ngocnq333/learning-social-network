package com.solution.ntq.service.impl;

import com.solution.ntq.model.ClassMember;
import com.solution.ntq.model.Clazz;
import com.solution.ntq.model.User;
import com.solution.ntq.repository.IClassMemberRepository;
import com.solution.ntq.repository.IClazzRepository;
import com.solution.ntq.service.IClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IClazzServiceImpl implements IClazzService {
    @Autowired
    IClazzRepository iClazzRepository;
    @Autowired
    IClassMemberRepository iClassMemberRepository;

    @Override
    public void addClazz(Clazz clazz) {
        iClazzRepository.save(clazz);

    }

    @Override
    public List<Clazz> getClassByUser(User user) {
        List<ClassMember> classMembers = iClassMemberRepository.findByUser(user);


        return classMembers.stream().map(s -> s.getClazz()).collect(Collectors.toList());
    }
}
