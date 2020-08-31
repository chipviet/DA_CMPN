package com.doan.cnpm.domain;

import com.doan.cnpm.domain.enumeration.WeekDay;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_day")
    private WeekDay weekDay;

    @Column(name = "start")
    private Long start;
}
