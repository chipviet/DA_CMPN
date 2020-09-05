package com.doan.cnpm.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name ="class")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_student")
    private Long id_student;

    @Column(name = "id_subject")
    private Long id_subject;

    @Column(name = "level")
    private Long level;

    @Column(name = "basic_tuition")
    private Long basic_tuition;

    @Column(name = "id_tutor")
    private Long id_tutor;

    @Column(name = "id_schedule")
    private Long id_schedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_student() {
        return id_student;
    }

    public void setId_student(Long id_student) {
        this.id_student = id_student;
    }

    public Long getId_subject() {
        return id_subject;
    }

    public void setId_subject(Long id_subject) {
        this.id_subject = id_subject;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getBasic_tuition() {
        return basic_tuition;
    }

    public void setBasic_tuition(Long basic_tuition) {
        this.basic_tuition = basic_tuition;
    }

    public Long getId_tutor() {
        return id_tutor;
    }

    public void setId_tutor(Long id_tutor) {
        this.id_tutor = id_tutor;
    }

    public Long getId_schedule() {
        return id_schedule;
    }

    public void setId_schedule(Long id_schedule) {
        this.id_schedule = id_schedule;
    }

}
