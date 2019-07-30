package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.repository.entities.User;
import org.springframework.data.repository.Repository;

import java.util.List;
/**
 * @author Duc Anh
 */
public interface ClazzMemberRepository extends Repository<ClazzMember,Integer> {
    List<ClazzMember> findByUserId(String userId);
    ClazzMember findByClazzIdAndIsCapitalTrue(int clazzId);
    List<ClazzMember> findByClazzIdAndIsApproveFalse(int clazzId);
    int countAllByClazzId(int id);

}
