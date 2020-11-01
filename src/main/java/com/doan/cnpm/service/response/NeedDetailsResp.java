package com.doan.cnpm.service.response;

import java.util.List;
import java.util.Set;

public class NeedDetailsResp {
    private Long id;

    private Long idUser;

    private Long level;

    private String subject;

    private String place;

    private List<ScheduleDetailResp> schedule;

    private Boolean status;

    private Long tuition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<ScheduleDetailResp> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleDetailResp> schedule) {
        this.schedule = schedule;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getTuition() {
        return tuition;
    }

    public void setTuition(Long tuition) {
        this.tuition = tuition;
    }
}
