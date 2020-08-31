package com.doan.cnpm.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level")
    private String level;

    @Column(name = "basicTuition")
    private Double basicTuition;

    @Column(name = "currency_code")
    private String currencyCode;
}
