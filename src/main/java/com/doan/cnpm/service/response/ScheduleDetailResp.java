package com.doan.cnpm.service.response;

import java.sql.Time;

public class ScheduleDetailResp {

    private String day;

    private Time lesson;

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
