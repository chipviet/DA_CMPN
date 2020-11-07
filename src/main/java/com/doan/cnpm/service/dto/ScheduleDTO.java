package com.doan.cnpm.service.dto;

import org.springframework.stereotype.Service;

@Service
public class ScheduleDTO {

    private Long id;

    private Long id_day;

    private Long id_lesson;

    private Double duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_day() {
        return id_day;
    }

    public void setId_day(Long weekday) {
        this.id_day = weekday;
    }

    public Long getId_lesson() {
        return id_lesson;
    }

    public void setId_lesson(Long lesson) {
        this.id_lesson = lesson;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }
}
