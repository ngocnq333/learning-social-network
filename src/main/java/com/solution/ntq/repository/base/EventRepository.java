package com.solution.ntq.repository.base;

import com.solution.ntq.repository.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * @author Ngoc Ngo Quy
 * @since  at 7/08/2019
 * @version 1.01
 */
@org.springframework.stereotype.Repository
public interface EventRepository extends Repository<Event,Integer> {
    @Transactional
    void save(Event event);

    @Query(value = "SELECT * FROM event e WHERE NOT e.id = ?4 AND e.clazz_id =?1 and e.start_date BETWEEN  ?2 AND ?3" +
            " ORDER BY e.start_date ASC ", nativeQuery = true)
    List<Event> getEventByClazzIdAndStartDateNotExistIgnore(int classId, Date startDate, Date endDate, int evenIgnoreId);

    @Query(value = "SELECT * FROM event e WHERE e.clazz_id =?1 and e.start_date BETWEEN  ?2 AND ?3", nativeQuery = true)
    List<Event> getEventByClazzIdAndStartDate(int classId, Date startDate, Date endDate);
    Event findById(int eventId);
    void deleteById(int eventId);
}