package com.doan.cnpm.repositories;

import com.doan.cnpm.domain.Subject;
import com.doan.cnpm.domain.TutorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s from Subject s where s.id= :id ")
    Subject findOneById (@Param("id") Long id);

    @Query("SELECT s from Subject s where s.nameSubject = :nameSubject ")
    Subject findOneBynameSubject (@Param("nameSubject") String nameSubject);


    @Transactional
    @Modifying
    @Query("DELETE from Subject s where s.nameSubject = :nameSubject ")
    void deleteBynameSubject(@Param("nameSubject") String nameSubject);

    @Transactional
    @Modifying
    @Query("DELETE from Subject s where s.id = :id ")
    void deleteById(@Param("id") Long id);
}
