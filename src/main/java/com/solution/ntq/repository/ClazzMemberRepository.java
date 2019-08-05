package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.ClazzMember;
import org.springframework.data.repository.Repository;

import java.util.List;
/**
 * @author Duc Anh
 */
public interface ClazzMemberRepository extends Repository<ClazzMember,Integer> {
    List<ClazzMember> findByUserId(String userId);
    List<ClazzMember> findByClazzId(int clazzId);
    ClazzMember findByClazzIdAndUserId(int clazzId,String userId);
    int countAllByClazzId(int clazzid);

    void save(ClazzMember clazzMember);


    ClazzMember findByClazzIdAndIsCaptainTrue(int id);
}
