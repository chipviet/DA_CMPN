package com.doan.cnpm.service.response;

import java.util.HashSet;
import java.util.Set;

public class CourseDetailResp {

    private Long id;

    private Long idNeed;

    private String nameTutor;

    private Set<String> student = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNeed() {
        return idNeed;
    }

    public void setIdNeed(Long idNeed) {
        this.idNeed = idNeed;
    }

    public String getIdTutor() {
        return nameTutor;
    }

    public void setIdTutor(String idTutor) {
        this.nameTutor = idTutor;
    }

    public Set<String> getStudent() {
        return student;
    }

    public void setStudent(Set<String> student) {
        this.student = student;
    }
}
