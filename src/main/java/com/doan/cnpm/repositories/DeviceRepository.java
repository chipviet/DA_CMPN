package com.doan.cnpm.repositories;

import com.doan.cnpm.domain.Device;
import com.doan.cnpm.domain.TutorDetails;
import com.doan.cnpm.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {


    @Query("SELECT t from Device t where t.user = :user ")
    List<Device> findByUser (@Param("user") User user);

}
