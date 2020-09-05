package com.doan.cnpm.repositories;


import com.doan.cnpm.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@SuppressWarnings("unused")
@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c from class c where c.id = :id ")
    Course findOneById (@Param("id") Integer id);

    @Query("SELECT c from class c where c.id_student = :id_student ")
    Course findOneByIdStudent (@Param("id_student") Integer id_student);

    @Query("SELECT c from class c where c.id_subject = :id_subject ")
    Course findOneByIdSubject (@Param("id_subject") Integer id_subject);

    @Query("DELETE from class c where c.id = :id ")
    void deleteById(@Param("id") Integer id);
}
