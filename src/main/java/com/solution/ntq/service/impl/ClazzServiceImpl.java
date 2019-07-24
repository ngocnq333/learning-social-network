package com.solution.ntq.service.impl;

import com.solution.ntq.model.ClassMember;
import com.solution.ntq.model.Clazz;
import com.solution.ntq.model.User;
import com.solution.ntq.repository.IClassMemberRepository;
import com.solution.ntq.repository.IClazzRepository;
import com.solution.ntq.service.IClazzService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClazzServiceImpl implements IClazzService {

    IClazzRepository iClazzRepository;

    IClassMemberRepository iClassMemberRepository;

    @Override
    public void addClazz(Clazz clazz) {
        iClazzRepository.save(clazz);

    }

    @Override
    public List<Clazz> getClassByUser(String userId) {
        List<ClassMember> classMembers = iClassMemberRepository.findByUserId(userId);
        return classMembers.stream().map(i -> i.getClazz()).collect(Collectors.toList());
    }

    @Override
    public User findCapitalByClass(Clazz clazz) {
        ClassMember classMember = iClassMemberRepository.findByClazzAndIsCapitalTrue(clazz);
        return classMember.getUser();
    }

    @Override
    public void addAllData() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = sdf.parse("06/10/2018");
        java.util.Date date1 = sdf.parse("06/10/2020");
        java.util.Date date2 = sdf.parse("05/06/2019");
        java.util.Date date3 = sdf.parse("06/06/2020");

        User user = new User();
        user.setId("a");
        user.setName("DucAnh");
        User user1 = new User();
        user1.setId("b");
        user1.setName("Manh");
        Clazz clazz = new Clazz();
        clazz.setName("Java");
        clazz.setDescription("Class about java");
        clazz.setStartDate(new Date(date.getTime()));
        clazz.setEndDate(new Date(date1.getTime()));
        clazz.setSlug("Slug Info");
        clazz.setThumbnail("Thumbnail Info");
        Clazz clazz1 = new Clazz();
        clazz1.setName("PHP");
        clazz1.setDescription("Class about PHP");
        clazz1.setStartDate(new Date(date2.getTime()));
        clazz1.setEndDate(new Date(date3.getTime()));
        clazz1.setSlug("Slug Info");
        clazz1.setThumbnail("Thumbnail Info");
        ClassMember classMember = new ClassMember();
        classMember.setClazz(clazz);
        classMember.setUser(user1);
        ClassMember classMember1 = new ClassMember();
        classMember1.setClazz(clazz1);
        classMember1.setCapital(true);
        classMember1.setUser(user1);
        ClassMember classMember2 = new ClassMember();
        classMember2.setClazz(clazz);
        classMember2.setUser(user);
        ClassMember classMember3 = new ClassMember();
        classMember3.setClazz(clazz1);
        classMember3.setCapital(true);
        classMember3.setUser(user);
        List<ClassMember> listClassMember = new ArrayList<>();
        listClassMember.add(classMember);
        listClassMember.add(classMember1);
        listClassMember.add(classMember2);
        listClassMember.add(classMember3);
        clazz.setClassMembers(listClassMember);
        clazz1.setClassMembers(listClassMember);
        user.setClassMembers(listClassMember);
        user1.setClassMembers(listClassMember);
        addClazz(clazz1);

    }

    @Override
    public Clazz getClassById(int clazzId) {

        return iClazzRepository.findClazzById(clazzId);
    }
}
