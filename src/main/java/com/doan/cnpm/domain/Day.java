package com.doan.cnpm.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name ="day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dayinweek")
    private String day;

    @OneToMany(mappedBy = "day",cascade = CascadeType.ALL)
    private Collection<Schedule> schedules;

}
