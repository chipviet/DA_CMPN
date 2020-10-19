package com.doan.cnpm.service.response;

import java.sql.Time;

public class ScheduleDetailResp {
    private Long id;

    private String day;

    private Time lesson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getLesson() {
        return lesson;
    }

    public void setLesson(Time lesson) {
        this.lesson = lesson;
    }
}
