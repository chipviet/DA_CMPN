package com.doan.cnpm.repositories;

import com.doan.cnpm.domain.Day;
import com.doan.cnpm.domain.RequestTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RequestTutorRepository extends JpaRepository<RequestTutor,Long> {
    @Query("SELECT r from RequestTutor r where r.idTutor = :idTutor")
    List<RequestTutor> findByIdTutor(@Param("idTutor")Long idTutor);

    @Query("SELECT r from RequestTutor r where r.idNeed = :idNeed")
    List<RequestTutor> findByIdNeed(@Param("idNeed")Long idNeed);
}
