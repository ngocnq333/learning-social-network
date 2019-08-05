package com.solution.ntq.repository;

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

    @Query(value = "SELECT * FROM Content c WHERE c.clazz_id = ?1  ORDER BY c.is_done ASC ,c.end_date DESC , c.start_date DESC ", nativeQuery = true)
    List<Content> findContentByIdClazz(int clazz);

    @Transactional
    void deleteById(int idContent);

    boolean existsById(int idContent);

}