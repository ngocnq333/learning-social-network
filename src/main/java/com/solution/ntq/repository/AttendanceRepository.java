package com.solution.ntq.repository;

import com.solution.ntq.repository.entities.Attendance;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
@Transactional
@org.springframework.stereotype.Repository
public interface AttendanceRepository extends Repository<Attendance, Integer> {
    void save(Attendance content);

    boolean existsById(int idAttendance);

    Attendance findAllById(int idAttendance);

}
