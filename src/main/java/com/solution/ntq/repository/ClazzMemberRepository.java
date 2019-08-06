package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.ClazzMember;
import com.solution.ntq.repository.entities.Clazz;
import com.solution.ntq.repository.entities.Content;
import com.solution.ntq.repository.entities.User;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Duc Anh
 */
@org.springframework.stereotype.Repository
public interface ClazzMemberRepository extends Repository<ClazzMember, Integer> {
    List<ClazzMember> findByUserId(String userId);

    ClazzMember findByClazzIdAndIsCaptainTrue(int clazzId);

    List<ClazzMember> findByClazzId(int clazzId);

    ClazzMember findByClazzIdAndUserId(int clazzId, String userId);

    int countAllByClazzId(int clazzid);

    void save(ClazzMember clazzMember);

    @Query(value = "SELECT e.id,c.clazz_id,e.user_id,e.is_capital,e.join_date,e.status " +
            "FROM clazz_member e  INNER JOIN content c ON c.clazz_id = e.clazz_id " +
            "WHERE e.is_capital = 0 AND c.id = ?1 AND e.status ='JOINED'", nativeQuery = true)
    List<ClazzMember> findAllByCapitalNot(int contentId);

}
