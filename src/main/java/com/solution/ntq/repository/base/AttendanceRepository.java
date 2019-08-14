package com.solution.ntq.repository.base;

import com.solution.ntq.repository.entities.Attendance;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @author Nam_Phuong
 * @version 1.01
 * @since 2019/07/31
 */
@Transactional
@org.springframework.stereotype.Repository
public interface AttendanceRepository extends Repository<Attendance, Integer> {
  List<Attendance> findByContentId(int contentId);
    void save(Attendance content);

    boolean existsById(int idAttendance);

    Attendance findAllById(int idAttendance);

}
