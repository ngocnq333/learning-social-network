package com.solution.ntq.repository;

import com.solution.ntq.model.ClassMember;
import com.solution.ntq.model.Clazz;
import org.springframework.data.repository.Repository;

import java.util.List;
/**
 * @author Duc Anh
 */
public interface IClassMemberRepository extends Repository<ClassMember,Integer> {
    List<ClassMember> findByUserId(String userId);
    ClassMember findByClazzAndIsCapitalTrue(Clazz clazz);

}
