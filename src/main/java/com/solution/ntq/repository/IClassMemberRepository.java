package com.solution.ntq.repository;

import com.solution.ntq.model.ClassMember;
import com.solution.ntq.model.Clazz;
import com.solution.ntq.model.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface IClassMemberRepository extends Repository<ClassMember,Integer> {
    List<ClassMember> findByUserId(String userId);
    ClassMember findByClazzAndIsCapitalTrue(Clazz clazz);

}
