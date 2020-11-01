package com.doan.cnpm.repositories;

import com.doan.cnpm.domain.Course;
import com.doan.cnpm.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT l from Lesson l where l.id= :id ")
    Lesson findOneById (@Param("id") Long id);
}
