package com.doan.cnpm.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;

@Data
@Entity
@Table(name ="lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson")
    private Time lesson;

    @OneToMany(mappedBy = "lessons",cascade = CascadeType.ALL)
    private Collection<Schedule> schedules;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getLesson() {
        return lesson;
    }

    public void setLesson(Time lesson) {
        this.lesson = lesson;
    }

    public Collection<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Collection<Schedule> schedules) {
        this.schedules = schedules;
    }
}
