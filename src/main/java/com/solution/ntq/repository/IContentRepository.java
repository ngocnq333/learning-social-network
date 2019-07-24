package com.solution.ntq.repository;

import com.solution.ntq.model.Content;
import org.springframework.data.repository.Repository;

public interface IContentRepository extends Repository<Content,Integer> {
    void save(Content content);
}
