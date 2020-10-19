package com.doan.cnpm.repositories;


import com.doan.cnpm.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.lang.Long;

import javax.transaction.Transactional;

@SuppressWarnings("unused")
@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s from Schedule s where s.id = :id ")
    Schedule findOneById (@Param("id") Long id);

}