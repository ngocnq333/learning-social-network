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
    ClazzMember findByClazzIdAndIsCaptainIsTrue(int clazzId);
    int countAllByClazzId(int classId);
    ClazzMember findByClazzIdAndIsCaptainTrue(int clazzId);
    List<ClazzMember> findByClazzId(int clazzId);
    ClazzMember findByClazzIdAndUserId(int clazzId, String userId);

    int countAllByClazzId(int clazzid);

    void save(ClazzMember clazzMember);

    @Query(value = "SELECT e.id,c.clazz_id,e.user_id,e.is_captain,e.join_date,e.status FROM clazz_member e " +
            " INNER JOIN content c ON c.clazz_id = e.clazz_id WHERE e.is_captain = 0 AND c.id = ?1 " +
            " AND e.status ='JOINED' AND e.user_id NOT IN (SELECT user_id from attendance where content_id = ?1)", nativeQuery = true)
    List<ClazzMember> findAllByCapitalFalse(int contentId);
}
