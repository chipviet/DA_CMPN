package com.doan.cnpm.repositories;

import com.doan.cnpm.domain.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DayRepository extends JpaRepository<Day,Long> {

}
