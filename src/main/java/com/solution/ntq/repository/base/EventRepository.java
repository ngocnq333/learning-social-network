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
    @Query(value = "SELECT * FROM event WHERE clazz_id = ?1  AND id IN (SELECT event_id FROM join_event) ORDER BY start_date DESC ", nativeQuery = true)
    List<Event> findByClazzHaveAttendances(int classId);

    @Query(value = "SELECT * FROM event WHERE clazz_id = ?1  AND id IN (SELECT event_id FROM join_event) AND title LIKE %?2% ORDER BY start_date DESC ", nativeQuery = true)
    List<Event> findByIdClazzAndTitleHaveAttendances(int classId, String title);
    Event findById(int eventId);
    void deleteById(int eventId);
}