package com.doan.cnpm.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="Authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

}
