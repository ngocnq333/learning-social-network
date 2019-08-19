package com.solution.ntq.repository.base;

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
    void deleteById(int clazzMemberId);

    @Query(value = "SELECT e.id,c.clazz_id,e.user_id,e.is_captain,e.join_date,e.status FROM clazz_member e " +
            " INNER JOIN content c ON c.clazz_id = e.clazz_id WHERE e.is_captain = 0 AND c.id = ?1 " +
            " AND e.status ='JOINED' AND e.user_id NOT IN (SELECT user_id from attendance where content_id = ?1)", nativeQuery = true)
    List<ClazzMember> findAllByCapitalFalse(int contentId);

    @Query(value = "SELECT clazz_member.id, clazz_member.clazz_id, clazz_member.user_id, clazz_member.join_date, clazz_member.status, clazz_member.is_captain " +
            "FROM mockproject.clazz_member JOIN mockproject.user ON clazz_member.user_id = user.id WHERE  clazz_id = ?1 AND status = ?2 AND is_captain = false ORDER BY user.name ASC ",nativeQuery = true)
    List<ClazzMember> findByClazzIdAndIsCaptainIsNot(int clazzId, String status);

    @Query(value = "SELECT * FROM clazz_member WHERE clazz_id = ?1 AND user_id = ?2 AND is_captain = 1" ,nativeQuery = true)
    ClazzMember isCaptain(int classId, String userId) ;
    List<ClazzMember> findByUserIdAndClazzId(String userId, int clazzId);
    @Query(value = " select count(*) FROM clazz_member WHERE clazz_id = ?1 AND status like 'WAITING_FOR_APPROVE'" ,nativeQuery = true)
    int countPedingMemberOfClazz(int clazzId);
}
