package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.Event;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ngoc Ngo Quy
 * created at 7/08/2019
 * @version 1.01
 */
@Transactional
@org.springframework.stereotype.Repository
public interface EventRepository extends Repository<Event,Integer> {
}
