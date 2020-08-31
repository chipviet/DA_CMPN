package com.doan.cnpm.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_subject")
    private String nameSubject;
}
