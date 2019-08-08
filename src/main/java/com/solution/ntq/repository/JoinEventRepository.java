package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.JoinEvent;
import org.springframework.data.repository.Repository;
/**
 * @author Ngoc Ngo Quy
 * @since  at 8/08/2019
 * @version 1.01
 */
@org.springframework.stereotype.Repository
public interface JoinEventRepository extends Repository<JoinEvent, Integer> {
    void save(JoinEvent joinEvent);
}
