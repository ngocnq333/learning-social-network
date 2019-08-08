package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.ClazzMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Duc Anh
 */
@org.springframework.stereotype.Repository
public interface ClazzMemberRepository extends Repository<ClazzMember,Integer> {
    ClazzMember save(ClazzMember clazzMember);
    List<ClazzMember> findByUserId(String userId);
    int countAllByClazzId(int classId);
    ClazzMember findByClazzIdAndIsCaptainTrue(int clazzId);
    List<ClazzMember> findByClazzId(int clazzId);
    ClazzMember findByClazzIdAndUserId(int clazzId, String userId);
    @Query(value = "SELECT clazz_member.id, clazz_member.clazz_id, clazz_member.user_id, clazz_member.join_date, clazz_member.status, clazz_member.is_captain " +
            "FROM mockproject.clazz_member JOIN mockproject.user ON clazz_member.user_id = user.id WHERE  clazz_id = ?1 AND status = 'joined' AND is_captain = false ORDER BY user.name ASC ",nativeQuery = true)
    List<ClazzMember> findByClazzIdAndIsCaptainIsNot(int classId);
}
