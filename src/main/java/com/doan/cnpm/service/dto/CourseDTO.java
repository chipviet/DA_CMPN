package com.doan.cnpm.service.dto;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Service
public class CourseDTO {

    private Long id;

    private Long idNeed;

    private Long idTutor;

    private Set<Long> student = new HashSet<>();

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

    public Long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    public Set<Long> getStudent() {
        return student;
    }

    public void setStudent(Set<Long> student) {
        this.student = student;
    }
}
