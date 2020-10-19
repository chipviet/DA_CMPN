package com.doan.cnpm.domain;


import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Data
@Entity
@Table(name ="schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_day")
    private Day day;

    @ManyToOne
    @JoinColumn(name = "id_lesson")
    private Lesson lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
}
