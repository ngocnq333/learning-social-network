package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.Content;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author Duc Anh
 */

public interface ContentRepository extends Repository<Content,Integer> {
    void save(Content content);
    Content findContentById(int contentId);
    List<Content> findAllByClazzIdAndIsApproveFalse(int classId);

}
