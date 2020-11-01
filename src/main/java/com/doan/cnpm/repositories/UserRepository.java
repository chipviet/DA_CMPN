package com.doan.cnpm.repositories;


import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findOneByUsername( String username);
//    @Query("SELECT t from User t where t.username = :username ")
//    User findOneByUsername (@Param("username") String username);

    Optional<User> findOneByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);


    @Query("SELECT t.id from User t where t.username = :username ")
    String getIdByUsername (@Param("username") String username);


}
