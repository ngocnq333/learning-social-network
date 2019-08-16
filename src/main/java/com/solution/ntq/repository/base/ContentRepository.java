package com.solution.ntq.repository.base;

import com.solution.ntq.repository.entities.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Duc Anh
 */

public interface ContentRepository extends Repository<Content,Integer> {
    void save(Content content);

    List<Content> findAllByClazzIdAndIsApproveFalse(int classId);

    Content findContentById(int idContent);

    @Query(value = "SELECT * FROM Content c WHERE c.clazz_id = ?1 AND is_approve =1 ORDER BY c.is_done ASC ,c.end_date DESC , c.start_date DESC ", nativeQuery = true)
    List<Content> findContentApproveByIdClazz(int classId);
    @Query(value = "SELECT * FROM Content c WHERE c.clazz_id = ?1 AND is_approve =0 ORDER BY c.is_done ASC ,c.end_date DESC , c.start_date DESC ", nativeQuery = true)
    List<Content> findContentNotApproveByIdClazz(int classId);


    @Transactional
    void deleteById(int idContent);

    @Query(value = "SELECT * FROM content WHERE clazz_id = ?1  AND id IN (SELECT content_id FROM attendance) ORDER BY end_date DESC , start_date DESC ", nativeQuery = true)
    List<Content> findContentByClazzHaveAttendances(int classId);

    @Query(value = "SELECT * FROM content WHERE clazz_id = ?1  AND id IN (SELECT content_id FROM attendance) AND title LIKE %?2% ORDER BY end_date DESC , start_date DESC ", nativeQuery = true)
    List<Content> findByIdClazzAndTitleHaveAttendances(int classId, String title);

    boolean existsById(int idContent);

}
