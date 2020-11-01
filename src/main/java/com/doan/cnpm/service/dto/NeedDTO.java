package com.doan.cnpm.service.dto;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NeedDTO {

    private Long id;

    private Long idUser;

    private Long level;

    private Long subject;

    private String place;


    private Boolean status;

    private Long tuition;

    private Set<ScheduleDTO> schedule;

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

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<ScheduleDTO> getSchedule() {
        return schedule;
    }

    public void setSchedule(Set<ScheduleDTO> schedule) {
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
