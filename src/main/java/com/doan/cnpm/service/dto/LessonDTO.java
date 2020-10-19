package com.doan.cnpm.service.dto;

import java.sql.Time;

public class LessonDTO {
    private Long id;
    private Time lesson;

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
}
