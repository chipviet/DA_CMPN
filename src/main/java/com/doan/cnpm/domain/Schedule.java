package com.doan.cnpm.domain;


import lombok.Data;

import javax.persistence.*;
import java.lang.Long;
import java.util.HashSet;
import java.util.Set;

//@Data
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
    private Lesson lessons;

    @ManyToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Need> needs = new HashSet<>();


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

    public Lesson getLessons() {
        return lessons;
    }

    public void setLessons(Lesson lessons) {
        this.lessons = lessons;
    }

    public Set<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(Set<Need> needs) {
        this.needs = needs;
    }
}
