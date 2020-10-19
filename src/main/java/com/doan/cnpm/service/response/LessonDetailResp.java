package com.doan.cnpm.service.response;

import java.sql.Time;

public class LessonDetailResp {
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
