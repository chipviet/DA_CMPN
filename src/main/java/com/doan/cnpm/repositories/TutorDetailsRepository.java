package com.doan.cnpm.repositories;


import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import com.doan.cnpm.service.response.TutorDetailsResp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.lang.Long;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TutorDetails domain.
 */
@SuppressWarnings("unused")
@Repository
@Transactional
public interface TutorDetailsRepository extends JpaRepository<TutorDetails, Long> {


//    @Query("SELECT new com.doan.cnpm.service.response.TutorDetailsResp (t.id, t.literacy, t.efficency) FROM TutorDetails t WHERE t.id = :tutorId")
//    TutorDetailsResp findOneById (@Param("tutorId") Double tutorId);

//    @Query("SELECT t from TutorDetails t where t.literacy = :literacyString ")
//    TutorDetails findOneById (@Param("literacyString") String literacyString);

    @Query("SELECT t from TutorDetails t where t.user = :user ")
    TutorDetails findOneByUserId (@Param("user") User user);

    @Query("SELECT t from TutorDetails t where t.id =: id ")
    TutorDetails findOneByTutorId (@Param("id") Long id);

    @Query("SELECT t from TutorDetails t where t.user = :user")
    TutorDetails findOneByUser(@Param("user")User user);

    @Transactional
    @Modifying
    @Query("DELETE from TutorDetails t where t.user = :idUser ")
    void deleteByUsername(@Param("idUser") Long idUser);



//    Optional<TutorDetails> findOneByUsername (String username);


}